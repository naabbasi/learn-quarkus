echo Generate a certificate/private key for JWT (resource folder)
openssl req -new -newkey rsa:2048 -nodes -keyout server.key -out server.csr  -subj "/C=PK/ST=Sind/L=Karachi/O=Matrix Systems pvt ltd/OU=Software Development/CN=noman"
echo Sign the certificate
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
echo Generate a certificate as pkcs12 format
openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name quarkus-ssl -CAfile server.crt -caname root -chain
