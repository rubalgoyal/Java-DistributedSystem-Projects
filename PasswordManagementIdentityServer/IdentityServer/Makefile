GRADLEW			?=	./gradlew
@port		?=	default_value
@address		?=	default_value
@certificate		?=	default_value
@authenticationKey		?=	default_value

build:
		@echo	"build"
		$(GRADLEW)	clean	shadowJar

clean:
		@echo	"clean"
		$(GRADLEW)	clean

runServer:	start-redis-server
		@echo	"run"
		java -cp build/libs/IdentityServer-1.0-SNAPSHOT-all.jar IdentityServer $(@port)

runClient:
		@echo	"run"
		java -cp build/libs/IdentityServer-1.0-SNAPSHOT-all.jar Client $(@address) $(@port)

start-redis-server:
		@echo "Started Redis server at default port 6379"
		redis-server &