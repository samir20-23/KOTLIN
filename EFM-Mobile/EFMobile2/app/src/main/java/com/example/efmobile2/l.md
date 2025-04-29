**شرح مفصّل لأهم الأسطر والاستيرادات في تطبيق Jetpack Compose CRUD**

```
app/src/main/java/com/example/efmobile2/
│
├─ model/
│   └ Formation.kt          // يحتوي على نموذج البيانات (data class)
├─ viewmodel/
│   └ FormationViewModel.kt // يحتوي المنطق (CRUD) وحالة الواجهة
├─ ui/
│   ├─ screens/
│   │   └ MainScreen.kt     // واجهة الشاشة الرئيسية
│   └─ components/
│       ├─ FormationItem.kt  // عنصر عرض لكل مهمة
│       └ FormationInput.kt  // نموذج إدخال/تعديل مهمة
└─ MainActivity.kt          // نقطة الانطلاق (Activity) وتعيين Compose
```

---

### model/Formation.kt
```kotlin
package com.example.efmobile2.model    // يحدد حزمة الكود

// تعريف كلاس بيانات يمثل "مهمة" (Formation)
data class Formation(
    val id: String = "",         // معرف فريد (UUID)
    val name: String = "",       // اسم المهمة
    val status: String = "",     // الحالة (مثلاً: "open" أو "done")
    val priority: String = ""    // الأولوية (مثلاً: "high", "low")
)
```

---

### viewmodel/FormationViewModel.kt
```kotlin
package com.example.efmobile2.viewmodel

import androidx.lifecycle.ViewModel             // ViewModel من Android Architecture
import androidx.compose.runtime.mutableStateListOf // قائمة قابلة للمراقبة في Compose
import com.example.efmobile2.model.Formation    // استيراد نموذج Formation

// كلاس مسؤول عن الاحتفاظ بالحالة وتنفيذ CRUD
class FormationViewModel: ViewModel() {
    private val _formations = mutableStateListOf<Formation>()
    val formations: List<Formation> = _formations // تعريض القائمة كقراءة فقط

    // إضافة مهمة جديدة
    fun addFormation(f: Formation) {
        _formations.add(f)
    }

    // حذف بناءً على id
    fun deleteFormation(id: String) {
        _formations.removeIf { it.id == id }
    }

    // تحديث مهمة موجودة
    fun updateFormation(f: Formation) {
        val i = _formations.indexOfFirst { it.id == f.id } // إيجاد الفهرس
        if (i != -1) _formations[i] = f                    // استبدال العنصر
    }
}
```

---

### ui/components/FormationInput.kt
```kotlin
package com.example.efmobile2.ui.components

import androidx.compose.material.Button     // زر
import androidx.compose.material.Text       // نص
import androidx.compose.material.TextField  // حقل إدخال نص
import androidx.compose.runtime.*           // state و Composable
import com.example.efmobile2.model.Formation
import com.example.efmobile2.viewmodel.FormationViewModel

@Composable // تعلِّم Compose بأن هذه دالة واجهة
fun FormationInput(
    viewModel: FormationViewModel,  // لربط العمليات
    current: Formation? = null      // إذا تعديل، تأتي المهمة الحالية
) {
    // حالات محلية لحفظ القيم المدخلة
    var name by remember { mutableStateOf(current?.name ?: "") }
    var status by remember { mutableStateOf(current?.status ?: "") }
    var priority by remember { mutableStateOf(current?.priority ?: "") }

    // حقول الإدخال
    TextField(value = name, onValueChange = { name = it }, placeholder = { Text("Name") })
    TextField(value = status, onValueChange = { status = it }, placeholder = { Text("Status") })
    TextField(value = priority, onValueChange = { priority = it }, placeholder = { Text("Priority") })

    // زر الإضافة أو التحديث
    Button(onClick = {
        // إذا current = null => إضافة جديد، وإلا تحديث
        val id = current?.id ?: java.util.UUID.randomUUID().toString()
        val f = Formation(id, name, status, priority)
        if (current == null) viewModel.addFormation(f) else viewModel.updateFormation(f)
    }) {
        Text(if (current == null) "Add" else "Update")
    }
}
```

---

### ui/components/FormationItem.kt
```kotlin
package com.example.efmobile2.ui.components

import androidx.compose.foundation.clickable       // لجعل العنصر قابل للنقر
import androidx.compose.foundation.layout.Row     // تخطيط أفقي
import androidx.compose.material.Icon            // أيقونة
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import com.example.efmobile2.model.Formation
import com.example.efmobile2.viewmodel.FormationViewModel

@Composable
fun FormationItem(
    f: Formation,
    viewModel: FormationViewModel,
    onEdit: (Formation) -> Unit // دالة تستدعى عند التعديل
) {
    Row(modifier = androidx.compose.ui.Modifier.clickable { onEdit(f) }) {
        Text(f.name) // عرض اسم المهمة
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            modifier = androidx.compose.ui.Modifier.clickable { onEdit(f) }
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            modifier = androidx.compose.ui.Modifier.clickable { viewModel.deleteFormation(f.id) }
        )
    }
}
```

---

### ui/screens/MainScreen.kt
```kotlin
package com.example.efmobile2.ui.screens

import androidx.compose.foundation.lazy.LazyColumn     // قائمة تمرير عمودية
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable // للحفظ عبر إعادة التكوين
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.efmobile2.viewmodel.FormationViewModel
import com.example.efmobile2.ui.components.FormationInput
import com.example.efmobile2.ui.components.FormationItem
import com.example.efmobile2.model.Formation

@Composable
fun MainScreen(viewModel: FormationViewModel) {
    // حفظ أي مهمة نعدلها
    var editing by rememberSaveable { mutableStateOf<Formation?>(null) }

    Surface(color = MaterialTheme.colors.background) {
        FormationInput(viewModel, editing) // عرض نموذج الإدخال
        LazyColumn {
            items(viewModel.formations) { f ->
                FormationItem(f, viewModel) { editing = it } // عند النقر على تعديل
            }
        }
    }
}
```

---

### MainActivity.kt
```kotlin
package com.example.efmobile2

import android.os.Bundle
import androidx.activity.ComponentActivity             // Activity أساسها ComponentActivity
import androidx.activity.compose.setContent          // لربط Compose
import com.example.efmobile2.ui.screens.MainScreen
import com.example.efmobile2.viewmodel.FormationViewModel

class MainActivity: ComponentActivity() {
    private val vm = FormationViewModel() // إنشاء ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(vm) }      // تعيين UI
    }
}
```

---

### Dependencies (module build.gradle)
```groovy
implementation "androidx.activity:activity-compose:1.7.0"            // دعم Compose في Activity
implementation "androidx.compose.ui:ui:1.4.0"                      // UI أساسي
implementation "androidx.compose.material:material:1.4.0"          // مكونات Material
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1" // ربط ViewModel مع Compose
```

مع هذا الشرح، يمكنك الآن فهم وظيفة كل استيراد وكل سطر رئيسي. أخبرني إذا تريد مزيد تفاصيل أو أمثلة عملية!```

