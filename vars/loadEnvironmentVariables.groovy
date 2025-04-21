/*
Load environment variables from passed-in parameters
List paramList
*/

def call(Map params) {
    withFolderProperties {
        paramsMap = []
        params.each {
            if (it.value && it.value.trim() != '') {
                paramsMap << "$it.key=$it.value"
            }
        }
    }
}
