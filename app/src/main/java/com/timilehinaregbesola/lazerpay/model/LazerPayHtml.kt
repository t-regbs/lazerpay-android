package com.timilehinaregbesola.lazerpay.model

import android.util.Base64

class LazerPayHtml {
    fun buildLazerPayHtml(params: LazerPayData): String {
        val businessLogoString =
            if (params.businessLogo != null && params.businessLogo.isNotEmpty()) "businessLogo: \"${params.businessLogo}\"," else ""
        val metadataString =
            if (params.metadata != null && params.metadata.isNotEmpty()) "metadata: \"${params.metadata}\"" else "metadata: {}"
        val unencodedHtml = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Lazerpay</title>
                <script src="https://cdn.jsdelivr.net/gh/LazerPay-Finance/checkout-build@main/checkout@1.0.1/dist/index.js" type="text/javascript"></script>
            </head>
            <body onload="setupLazerCheckout()" style="border-radius: 20px; background-color:#fff;height:100vh;overflow: hidden; ">
                <script type="text/javascript">
                    window.androidObj = function AndroidClass(){};
                    // Send callback to Android
                      window.androidObj.sendMessage = function(message) {
                          lazerpayClient.messageFromWeb(JSON.stringify(message))
                      }
                    // Send raw callback to dart JSMessageClient
                      window.androidObj.sendMessageRaw = function(message) {
                          lazerpayClient.rawMessageFromWeb(message)
                      }
                    
                    // Override JS Fetch
                    (function (ns, fetch) {
                      if (typeof fetch !== 'function') return;
                      ns.fetch = function () {
                        var out = fetch.apply(this, arguments);
                        // side-effect
                        out.then(async (ok) => {
                          var data = await ok.clone().json();
                          window.androidObj.sendMessage({
                              "type": "ON_FETCH",
                              "data": { ...data },
                          })
                        });
                        return out;
                      }
                    }(window, window.fetch))
                    window.onload = setupLazerCheckout;
                    function setupLazerCheckout() {
                        // Override default JS Console function
                        console._log_old = console.log
                        console.log = function(msg) {
                            window.androidObj.sendMessageRaw(msg);
                            console._log_old(msg);
                        }
                        console._error_old = console.error
                        console.error = function(msg) {
                            window.androidObj.sendMessageRaw(msg);
                            console._error_old(msg);
                        }
                         LazerCheckout({
                          name: "${params.name}",
                          email: "${params.email}",
                          amount: "${params.amount}",
                          key: "${params.publicKey}",
                          reference: "${params.reference}",
                          acceptPartialPayment:${params.acceptPartialPayment},
                          currency: "${params.currency!!.name}",
                          $businessLogoString
                          $metadataString,
                          onClose: (data) => window.androidObj.sendMessage({
                                "type": "ON_CLOSE",
                              }),
                          onSuccess: (data) => window.androidObj.sendMessage({
                                "type": "ON_SUCCESS",
                                "data": { ...data },
                              }),
                          onError: (data) => window.androidObj.sendMessage({
                                "type": "ON_ERROR",
                                "data": { ...data },
                              }),
                        });
                    // Add EventListener for onMessage Event
                      window.addEventListener("click", (event) => {
                      let path = event.composedPath()[0].innerHTML;
                      if (path == "Copied" || path == "Copy")
                          window.androidObj.sendMessage({"type": "ON_COPY", "data": path}) 
                      });
                    }
                </script>
            </body>
            </html>
        """.trimIndent()
        return Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
    }
}
