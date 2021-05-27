#include <jni.h>
#include <cstring>
#include <cstdio>

static const char apiUrlDebug[] = "https://tvstructureapi.herokuapp.com/";
static const char apiUrlProduct[] = "https://tvstructureapi.herokuapp.com/";

extern "C"
JNIEXPORT jstring JNICALL
Java_com_thedung_androidtvstructure_di_module_NetworkModule_getBaseUrl(JNIEnv *env, __unused jobject thiz, jboolean debug) {
    if (debug)
        return env->NewStringUTF(apiUrlDebug);
    return env->NewStringUTF(apiUrlProduct);
}