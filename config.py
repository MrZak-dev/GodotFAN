def can_build(env,plat):
    return plat=="android"

def configure(env):
    if env['platform'] == 'android':
        env.android_add_java_dir("src")
        #env.android_add_maven_repository("https://repo.maven.apache.org/maven2")
        env.android_add_dependency("compile 'com.android.support:recyclerview-v7:27.1.1'")
        env.android_add_dependency("compile 'com.facebook.android:audience-network-sdk:5.+'")