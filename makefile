JFLAGS = -g -Xlint
JC = javac
JVM = java
ID = 0
N = 3
DELAY = 10000
BEARER = true

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	Token.java \
	Interfaz.java \
	Servidor.java \
	Main.java \

MAIN = Main

#default: classes
#	$(JVM) $(MAIN)

classes: $(CLASSES:.java=.class)

run: classes
ifeq ($(MAIN),Servidor)
	$(JVM) $(MAIN)
else
	$(JVM) $(MAIN) $(ID) $(N) $(DELAY) $(BEARER)
endif

clean:
	$(RM) *.class
