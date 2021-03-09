## Kotlin 入门 - 2

## 1. 使用 Fragment

下文使用 `Fragment` 示例突出介绍 Kotlin 的一些最佳功能。

### 1.1. 继承

您可以使用 `class` 关键字在 Kotlin 中声明类。在以下示例中，`LoginFragment` 是 `Fragment` 的子类。您可以通过在子类与其父类之间使用 `:` 运算符指示继承：

```kotlin
class LoginFragment : Fragment()
```

在此类声明中，`LoginFragment` 负责调用其超类 `Fragment` 的构造函数。

在 `LoginFragment` 中，您可以替换许多生命周期回调以响应 `Fragment` 中的状态变化。如需替换函数，请使用 `override` 关键字，如以下示例所示：

```kotlin
override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
): View? {
    return inflater.inflate(R.layout.login_fragment, container, false)
}
```

如需引用父类中的函数，请使用 `super` 关键字，如以下示例所示：

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {  super.onViewCreated(view, savedInstanceState)}
```

### 1.2. 可为 null 性和初始化

在前面的示例中，被替换的方法中某些参数的类型以问号 `?` 为后缀。这表示为这些参数传递的实际参数可以为 null。请务必[安全地处理其可为 null 性](https://kotlinlang.org/docs/reference/null-safety.html)。

在 Kotlin 中，您必须在声明对象时初始化对象的属性。这意味着，当您获取类的实例时，可以立即引用它的任何可访问属性。不过，在调用 `Fragment#onCreateView` 之前，`Fragment` 中的 `View` 对象尚未准备好进行扩充，所以您需要一种方法来推迟 `View` 的属性初始化。

您可以使用 `lateinit` 推迟属性初始化。使用 `lateinit` 时，您应尽快初始化属性。

以下示例演示了如何使用 `lateinit` 在 `onViewCreated` 中分配 `View` 对象：

```kotlin
class LoginFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var statusTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.username_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        loginButton = view.findViewById(R.id.login_button)
        statusTextView = view.findViewById(R.id.status_text_view)
    }

    ...
}
```

**注意**：如果您在初始化属性之前对其进行访问，Kotlin 会抛出 `UninitializedPropertyAccessException`。

### 1.3. SAM 转换

您可以通过实现 `OnClickListener` 接口来监听 Android 中的点击事件。`Button` 对象包含一个 `setOnClickListener()` 函数，该函数接受 `OnClickListener` 的实现。

`OnClickListener` 具有单一抽象方法 `onClick()`，您必须实现该方法。因为 `setOnClickListener()` 始终将 `OnClickListener` 当作参数，又因为 `OnClickListener` 始终都有相同的单一抽象方法，所以此实现在 Kotlin 中可以使用匿名函数来表示。此过程称为[单一抽象方法转换](https://kotlinlang.org/docs/reference/java-interop.html#sam-conversions)或 SAM 转换。

SAM 转换可使代码明显变得更简洁。以下示例展示了如何使用 SAM 转换来为 `Button` 实现 `OnClickListener`：

```kotlin
loginButton.setOnClickListener {
    val authSuccessful: Boolean = viewModel.authenticate(
            usernameEditText.text.toString(),
            passwordEditText.text.toString()
    )
    if (authSuccessful) {
        // Navigate to next screen
    } else {
        statusTextView.text = requireContext().getString(R.string.auth_failed)
    }
}
```

当用户点击 `loginButton` 时，系统会执行传递给 `setOnClickListener()` 的匿名函数中的代码。

### 1.4. 伴生对象

[伴生对象](https://kotlinlang.org/docs/tutorials/kotlin-for-py/objects-and-companion-objects.html#companion-objects)提供了一种机制，用于定义在概念上与某个类型相关但不与某个特定对象关联的变量或函数。伴生对象类似于对变量和方法使用 Java 的 `static` 关键字。

在以下示例中，`TAG` 是一个 `String` 常量。您不需要为每个 `LoginFragment` 实例定义一个唯一的 `String` 实例，因此您应在伴生对象中定义它：

```kotlin
class LoginFragment : Fragment() {

    ...

    companion object {
        private const val TAG = "LoginFragment"
    }
}
```

您可以在文件的顶级定义 `TAG`，但文件中可能有大量的变量、函数和类也是在顶级定义的。伴生对象有助于连接变量、函数和类定义，而无需引用该类的任何特定实例。

### 1.5. 属性委托

初始化属性时，您可能会重复 Android 的一些比较常见的模式，例如在 `Fragment` 中访问 `ViewModel`。为避免过多的重复代码，您可以使用 Kotlin 的属性委托语法。

```kotlin
private val viewModel: LoginViewModel by viewModels()
```

属性委托提供了一种可在您的整个应用中重复使用的通用实现。Android KTX 为您提供了一些属性委托。例如，`viewModels` 可检索范围限定为当前 `Fragment` 的 `ViewModel`。

属性委托使用反射，这样会增加一些性能开销。这种代价换来的是简洁的语法，可让您节省开发时间。

## 2. 是否可为 null

Kotlin 提供了严格的可为 null 性规则，可在您的整个应用中维护类型安全。在 Kotlin 中，默认情况下，对对象的引用不能包含 null 值。如需为变量赋 null 值，必须通过将 `?` 添加到基本类型的末尾以声明可为 null 变量类型。

例如，以下表达式在 Kotlin 中是违反规则的。`name` 的类型为 `String`，不可为 null：

```kotlin
val name: String = null
```

如需允许 null 值，必须使用可为 null `String` 类型 `String?`，如以下示例所示：

```kotlin
val name: String? = null
```

### 2.1. 互操作性

Kotlin 的严格规则可使代码更安全且更简洁。这些规则可降低会导致应用崩溃的 `NullPointerException` 出现的几率。此外，它们还可减少您需要在代码中进行的 null 检查的次数。

通常，在编写 Android 应用时，您还必须调用非 Kotlin 代码，因为大多数 Android API 都是用 Java 编程语言编写的。

可为 null 性是 Java 和 Kotlin 在行为上有所不同的一个主要方面。Java 对可为 null 性语法的要求不那么严格。

例如，`Account` 类具有一些属性，包括一个名为 `name` 的 `String` 属性。Kotlin 制定了与可为 null 性有关的规则，Java 没有制定这样的规则，而是依赖于可选的可为 null 性注释明确声明您是否可以赋予 null 值。

由于 Android 框架主要是用 Java 编写的，因此在调用没有可为 null 性注释的 API 时，您可能会遇到这种情况。

### 2.2. 平台类型

如果您使用 Kotlin 引用在 Java `Account` 类中定义的不带注释的 `name` 成员，编译器将不知道 `String` 映射到 Kotlin 中的 `String` 还是 `String?`。这种不明确性通过平台类型 `String!` 表示。

`String!` 对 Kotlin 编译器而言没有特殊的含义。`String!` 可以表示 `String` 或 `String?`，编译器可让您赋予任一类型的值。请注意，如果您将类型表示为 `String` 并赋予 null 值，则系统可能会抛出 `NullPointerException`。

为了解决此问题，每当您用 Java 编写代码时，都应使用可为 null 性注释。这些注释对 Java 和 Kotlin 开发者都有帮助。

例如，下面是在 Java 中定义的 `Account` 类：

```java
public class Account implements Parcelable {
    public final String name;
    public final String type;
    private final @Nullable String accessId;

    ...
}
```

其中一个成员变量 `accessId` 带有 `@Nullable` 注释，这表示它可以持有 null 值。于是，Kotlin 会将 `accessId` 视为 `String?`。

如需指明变量绝不能为 null，请使用 `@NonNull` 注释：

```java
public class Account implements Parcelable {
    public final @NonNull String name;
    ...
}
```

在这种情况下，`name` 在 Kotlin 中被视为不可为 null `String`。

可为 null 性注释包含在所有新增的 Android API 以及许多现有的 Android API 中。许多 Java 库已添加可为 null 性注释，以便为 Kotlin 和 Java 开发者提供更好的支持。

### 2.3. 处理可为 null 性

如果您不确定 Java 类型，则应将其视为可为 null。例如，`Account` 类的 `name` 成员不带注释，因此您应假定它是一个可为 null `String`。

如果希望修剪 `name` 以使其值不包含前导或尾随空格，则可以使用 Kotlin 的 `trim` 函数。您可以通过几种不同的方式安全地修剪 `String?`。其中一种方式是使用非 null 断言运算符 `!!`，如以下示例所示：

```kotlin
val account = Account("name", "type")
val accountName = account.name!!.trim()
```

`!!` 运算符将其左侧的所有内容视为非 null，因此，在本例中，应将 `name` 视为非 null `String`。如果它左侧表达式的结果为 null，则您的应用会抛出 `NullPointerException`。此运算符简单快捷，但应谨慎使用，因为它会将 `NullPointerException` 的实例重新引入您的代码。

更安全的选择是使用安全调用运算符 `?.`，如以下示例所示：

```kotlin
val account = Account("name", "type")
val accountName = account.name?.trim()
```

使用安全调用运算符时，如果 `name` 不为 null，则 `name?.trim()` 的结果是一个不带前导或尾随空格的名称值。如果 `name` 为 null，则 `name?.trim()` 的结果为 `null`。这意味着，在执行此语句时，您的应用永远不会抛出 `NullPointerException`。

虽然安全调用运算符可使您避免潜在的 `NullPointerException`，但它会将 null 值传递给下一个语句。您可以使用 Elvis 运算符 (`?:`) 紧接着处理 null 值的情况，如以下示例所示：

```kotlin
val account = Account("name", "type")
val accountName = account.name?.trim() ?: "Default name"
```

如果 Elvis 运算符左侧表达式的结果为 null，则会将右侧的值赋予 `accountName`。此方法对于提供本来为 null 的默认值很有用。

您还可以使用 Elvis 运算符提前从函数返回结果，如以下示例所示：

```kotlin
fun validateAccount(account: Account?) {
    val accountName = account?.name?.trim() ?: "Default name"

    // account cannot be null beyond this point
    account ?: return

    ...
}
```

### 2.4. Android API 变更

Android API 对 Kotlin 的支持力度越来越高。Android 的许多最常见的 API（包括 `AppCompatActivity` 和 `Fragment`）包含可为 null 性注释，并且某些调用（如 `Fragment#getContext`）具有更支持 Kotlin 的替代调用。

例如，访问 `Fragment` 的 `Context` 几乎总是不为 null，因为您在 `Fragment` 中进行的大多数调用都是在 `Fragment` 附加到 `Activity`（`Context` 的子类）时发生的。即便如此，`Fragment#getContext` 并不总是返回非 null 值，因为在某些情况下 `Fragment` 未附加到 `Activity`。因此，`Fragment#getContext` 的返回类型可为 null。

由于从 `Fragment#getContext` 返回的 `Context` 可为 null（并且带有 @Nullable 注释），因此您必须在 Kotlin 代码中将其视为 `Context?`。这意味着，在访问其属性和函数之前，需要应用前面提到的某个运算符来处理可为 null 性问题。对于一些这样的情况，Android 包含可提供这种便利的替代 API。例如，`Fragment#requireContext` 会返回非 null `Context`，如果在 `Context` 将为 null 时调用它，则会抛出 `IllegalStateException`。这样，您就可以将生成的 `Context` 视为非 null 值，而无需使用安全调用运算符或其他解决方法。

### 2.5. 属性初始化

默认情况下，Kotlin 中的属性并未初始化。当初始化属性的封闭类时，必须初始化属性。

您可以通过几种不同的方式来初始化属性。以下示例展示了如何通过在类声明中为 `index` 变量赋值初始化该变量：

```kotlin
class LoginFragment : Fragment() {
    val index: Int = 12
}
```

此初始化也可以在初始化式块中进行定义：

```kotlin
class LoginFragment : Fragment() {
    val index: Int

    init {
        index = 12
    }
}
```

上面的示例中，在构建 `LoginFragment` 时初始化 `index`。

不过，某些属性可能无法在对象构建期间进行初始化。例如，您可能要从 `Fragment` 中引用 `View`，这意味着，必须先扩充布局。构建 `Fragment` 时不会发生扩充，而是在调用 `Fragment#onCreateView` 时进行扩充。

应对这种情况的一种方法是将视图声明为可为 null 并尽快对其进行初始化，如以下示例所示：

```kotlin
class LoginFragment : Fragment() {
    private var statusTextView: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            statusTextView = view.findViewById(R.id.status_text_view)
            statusTextView?.setText(R.string.auth_failed)
    }
}
```

虽然这样可以发挥预期的作用，但现在每当引用 `View` 时，都必须管理其可为 null 性。更好的解决方案是对 `View` 初始化使用 `lateinit`，如以下示例所示：

```kotlin
class LoginFragment : Fragment() {
    private lateinit var statusTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            statusTextView = view.findViewById(R.id.status_text_view)
            statusTextView.setText(R.string.auth_failed)
    }
}
```

通过 `lateinit` 关键字，可以避免在构建对象时初始化属性。如果在属性进行初始化之前对其进行了引用，Kotlin 会抛出 `UninitializedPropertyAccessException`，因此请务必尽快初始化属性。

**注意**：诸如[数据绑定](https://developer.android.google.cn/topic/libraries/data-binding)之类的视图绑定解决方案可以消除对 `findViewById` 的手动调用，这些解决方案有助于减少您需要考虑的 null 安全问题数量。
