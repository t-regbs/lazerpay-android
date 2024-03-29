# Lazerpay - Android SDK

Get paid in crypto from anywhere and withdraw instantly to your bank account, or invest your revenue to earn more with Lazerpay from inside an Android App. 

## ScreenShots
<p float="left">
<img src="https://github.com/t-regbs/lazerpay-android/blob/main/images/LA3.jpeg?raw=true" width="200">
<img src="https://github.com/t-regbs/lazerpay-android/blob/main/images/LA5.jpeg?raw=true" width="200">
<img src="https://github.com/t-regbs/lazerpay-android/blob/main/images/LA2.jpeg?raw=true" width="200">
<img src="https://github.com/t-regbs/lazerpay-android/blob/main/images/LA1.jpg?raw=true" width="200">
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
        name = "Erik Ten Hag",
        email = "eth@manutd.com",
        amount = "50000000", // default currency is Naira (NGN)
        publicKey = "pk_test_w3r3w1nningth3leagu3" // replace with your personal public key 
    )

```


* Set optional parameters such as your business logo, transaction currency, metadata, reference based on your specific use case: 

```kotlin 
     lazerpayBuilder.apply {
            reference("for Frenki De Jong") // ensure it is unique to every transaction
            businessLogo("https://securecdn.pymnts.com/wp-content/uploads/2021/12/stablecoins.jpg")
            currency(LazerPayCurrency.USD) // defaults to NGN available currencies: LazerPayCurrency.USD, LazerPayCurrency.NGN
            acceptPartialPayment(true) // default false 
            metadata("Erik Ten Hag's tricky reds")
     }
```

> 💥 All parameters in the apply block have default values and so the apply block can be omitted all together if you have no parameters to set

<br>


* Build the LazerpaySdk.Builder instance and assign it to the lateint property that was declared at the top of the file like so: 

```kotlin
    lazerPaySdk = lazerpayBuilder.build()
```


<br>

* Finally initialise the sdk instance with the `LazerPayResultListener` object declared earlier: 

```kotlin
    lazerPaySdk.initialize(resultListener)
```

> 🚨 Ensure that the **lazerPaySdk.initialize(resultListener)** function is called before the activity is started to avoid a Crash. Hence, it is best called within the onCreate() lifecycle method of your activity

<br>

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

```
MIT License

Copyright (c) 2022 LazerPay LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
