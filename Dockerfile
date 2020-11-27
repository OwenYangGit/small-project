FROM maven:3.6.3-openjdk-11
LABEL writter=diviner
RUN curl -fsSL https://code-server.dev/install.sh | sh
CMD code-server --bind-addr 0.0.0.0:80 --auth none
