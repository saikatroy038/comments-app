# comments-app

<b>install RabbitMQ and create queues post_queue, like_queue</b> <br>
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

<b>install postgreSQL</b> <br>
docker run -d -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=postgres -e POSTGRES_DB=postgres postgres -c 'max_connections=100'

