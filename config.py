def can_build(env,plat):
    return plat=="android"

def configure(env):
    if env['platform'] == 'android':
        env.android_add_java_dir("src")
        #env.android_add_maven_repository("https://repo.maven.apache.org/maven2")
        env.android_add_dependency("implementation 'com.android.support:recyclerview-v7:25.3.1'")
        env.android_add_dependency("implementation 'com.facebook.android:audience-network-sdk:5.+'")