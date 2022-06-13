package com.timilehinaregbesola.lazerpay.model

import android.util.Base64

class LazerPayHtml {
    fun buildLazerPayHtml(params: LazerPayData): String {
        val businessLogoString =
            if (params.businessLogo!!.isNotEmpty()) "businessLogo: \"${params.businessLogo}\"," else ""
        val metadataString =
            if (params.metadata!!.isNotEmpty()) "metadata: \"${params.metadata}\"" else "metadata: {}"
        val unencodedHtml =
            "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Lazerpay</title>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/gh/LazerPay-Finance/checkout-build@main/checkout@1.0.1/dist/index.js\" type=\"text/javascript\"></script>\n" +
                "</head>\n" +
                "<body onload=\"setupLazerCheckout()\" style=\"border-radius: 20px; background-color:#fff;height:100vh;overflow: hidden; \">\n" +
                "    <script type=\"text/javascript\">\n" +
                // Send callback to dart JSMessageClient
                "          function sendMessage(message) {\n" +
                "              if (window.LazerPayClientInterface && window.LazerPayClientInterface.postMessage) {\n" +
                "                  sendMessageRaw(JSON.stringify(message));\n" +
                "              }\n" +
                "          }\n" +
                // Send raw callback to dart JSMessageClient
                "          function sendMessageRaw(message) {\n" +
                "              if (window.LazerPayClientInterface && window.LazerPayClientInterface.postMessage) {\n" +
                "                  LazerPayClientInterface.postMessage(message);\n" +
                "              }\n" +
                "          }\n" +
                "        \n" +
                // Override JS Fetch
                "        (function (ns, fetch) {\n" +
                "          if (typeof fetch !== 'function') return;\n" +
                "          ns.fetch = function () {\n" +
                "            var out = fetch.apply(this, arguments);\n" +
                // side-effect
                "            out.then(async (ok) => {\n" +
                "              var data = await ok.clone().json();\n" +
                "              sendMessage({\n" +
                "                  \"type\": \"ON_FETCH\",\n" +
                "                  \"data\": { ...data },\n" +
                "              })\n" +
                "            });\n" +
                "            return out;\n" +
                "          }\n" +
                "        }(window, window.fetch))\n" +
                "        window.onload = setupLazerCheckout;\n" +
                "        function setupLazerCheckout() {\n" +
                "            // Override default JS Console function\n" +
                "            console._log_old = console.log\n" +
                "            console.log = function(msg) {\n" +
                "                sendMessageRaw(msg);\n" +
                "                console._log_old(msg);\n" +
                "            }\n" +
                "            console._error_old = console.error\n" +
                "            console.error = function(msg) {\n" +
                "                sendMessageRaw(msg);\n" +
                "                console._error_old(msg);\n" +
                "            }\n" +
                "             LazerCheckout({\n" +
                "              name: \"${params.name}\",\n" +
                "              email: \"${params.email}\",\n" +
                "              amount: \"${params.amount}\",\n" +
                "              key: \"${params.publicKey}\",\n" +
                "              reference: \"${params.reference}\",\n" +
                "              acceptPartialPayment: ${params.acceptPartialPayment},\n" +
                "              currency: \"${params.currency.name}\",\n" +
                "              $businessLogoString\n" +
                "              metadata: $metadataString,\n" +
                "              onClose: (data) => sendMessage({\n" +
                "                    \"type\": \"ON_CLOSE\",\n" +
                "                  }),\n" +
                "              onSuccess: (data) => sendMessage({\n" +
                "                    \"type\": \"ON_SUCCESS\",\n" +
                "                    \"data\": { ...data },\n" +
                "                  }),\n" +
                "              onError: (data) => sendMessage({\n" +
                "                    \"type\": \"ON_ERROR\",\n" +
                "                    \"data\": { ...data },\n" +
                "                  }),\n" +
                "            });\n" +
                // Add EventListener for onMessage Event
                "          window.addEventListener(\"click\", (event) => {\n" +
                "          let path = event.composedPath()[0].innerHTML;\n" +
                "          if (path == \"Copied\" || path == \"Copy\")\n" +
                "              sendMessage({\"type\": \"ON_COPY\", \"data\": path}) \n" +
                "          });\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>"
        return Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
    }
}
