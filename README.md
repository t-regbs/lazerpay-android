# Lazerpay - Android SDK

Get paid in crypto from anywhere and withdraw instantly to your bank account, or invest your revenue to earn more with Lazerpay from inside an Android App. 

## ScreenShots
<p float="left">
<img src="https://github.com/t-regbs/lazerpay-android/blob/1Soyebo-readMe/images/LA3.jpeg?raw=true" width="200">
<img src="https://github.com/t-regbs/lazerpay-android/blob/1Soyebo-readMe/images/LA4.jpeg?raw=true" width="200">
<img src="https://github.com/t-regbs/lazerpay-android/blob/1Soyebo-readMe/images/LA2.jpeg?raw=true" width="200">
<img src="https://github.com/t-regbs/lazerpay-android/blob/1Soyebo-readMe/images/LA1.jpg?raw=true" width="200">
</p>

##  Installation

### - Requirements 

* Android 5.0 (API level 21) and above
* [Android Gradle Plugin](https://developer.android.com/studio/releases/gradle-plugin) 7.2.0

### - Configuration

Add `lazerpay-android` to your module level `build.gradle` dependencies.

```
dependencies {
    implementation("io.github.t-regbs:lazerpay-android:0.1.0")
}
```

## Getting Started

* Declare a `LazerPaySdk` lateinit property at the very top of your activity or fragment like so: 

```kotlin
    private lateinit var lazerPaySdk: LazerPaySdk
```

* Configure the LazerPaySDK with a `LazerPayResultListener` object that handles transaction events from the sdk instance : 

```kotlin
    val resultListener = object : LazerPayResultListener {
            override fun onSuccess(result: SuccessData) {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction complete - Ref: ${result.reference}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Success", result.toString())
            }

            override fun onError(exception: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction failed",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onCancelled() {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction cancelled by user",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
```

* Setup the LazerPaySDK.Builder initializer that creates an instance of the `LazerpaySDK`. This would contain all the necessary transaction parameters like name, email, amount and your lazerpayPublicKey: 

```kotlin

    val lazerpayBuilder = LazerPaySdk.Builder(
        activity = this,
        name = "Frankie De Jong",
        email = "zuzuzeh@mail.com",
        amount = "5000", // default currency is Naira (NGN)
        publicKey = "pk_test_" // replace with your personal public key 
    )

```

* Include additional information such as your business logo and reference based on your specific use case: 

```kotlin 
    lazerpayBuilder.apply {
        reference("reference0") // ensure your reference is unique to every transaction
        businessLogo("https://securecdn.pymnts.com/wp-content/uploads/2021/12/stablecoins.jpg")
    }
```

* Build the LazerpaySdk.Builder instance and assign it to the lateint property that was declared at the top of the file like so: 

```kotlin
    lazerPaySdk = lazerpayBuilder.build()
```

* Finally initialise the sdk instance with the `LazerPayResultListener` object declared earlier: 

```kotlin
    lazerPaySdk.initialize(resultListener)
```

* 🚀 Ultimately to present the LazerPaySDK view and start collecting payments, add the line below to the onClickListener of a button for example: 

```kotlin
    lazerPaySdk.charge()
```

## Building the Example Project

1. Clone this repository.
2. Open the project using Android Studio 
3. Navigate to the `MainActivity.java` file
4. Replace the public key in the LazerPaySdk.Builder initializer with your own 
5. Replace the reference string in the lazerpayBuilder.apply closure with a unique string to avoid the `reference exists` error
6. Build and run the project on your device or emulator

## Want to contribute?
Feel free to create issues and pull requests. 🧑🏾‍🍳

## License
