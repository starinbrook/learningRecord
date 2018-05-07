import web  
from jpype import *
from threading import current_thread

urls = (  
    '/(.*)', 'hello'
)  
  
app = web.application(urls, globals())  
  
class hello:  
    def GET(self, name):
        thread = current_thread()
        print  thread.getName()

        ext_classpath = "D:\\testWork\\hello\\classes"
        jvmArg = "-Djava.class.path=" + ext_classpath
        print getDefaultJVMPath()
        print isJVMStarted()
        if not isJVMStarted():
            startJVM(getDefaultJVMPath(), jvmArg)
        print isJVMStarted()

        print isThreadAttachedToJVM()
        if not isThreadAttachedToJVM():
            attachThreadToJVM()
        print isThreadAttachedToJVM()
        hello = JClass("com.zhuyca.hello.Hello")
        hello.hello("hello world in JVM");

        if not name:   
            name = 'world'
        return 'Hello, ' + name + '!'
  
if __name__ == "__main__":
    app.run()
