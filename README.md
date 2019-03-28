# Äänestysjärjestelmä, backend

Tupas-autentikoinnin kautta äänestämisen mahdollistava sovellus.


## websequencediagram
title Äänestyksen Sekvenssidiagrammi
Käyttäjä->+Frontend:Äänestä-nappi
Frontend->+Backend:/tupas/
Backend->-Frontend:tupas-datat ja url
Frontend->-Käyttäjä:tupas-datat ja URL
Käyttäjä->+Tupas-testbench:/tupas/identify
Tupas-testbench->-Käyttäjä:HTML-sivu
Käyttäjä->+Tupas-testbench:/tupas/verify
Tupas-testbench->-Käyttäjä:Redirect to RETLINK
Käyttäjä->+Frontend:/RETLINK
Frontend->+Backend:/jokurajapinta/
Backend->-Frontend:JWT-token
Frontend->-Käyttäjä:JWT-token
Käyttäjä->+Frontend:Vahvista ääni
Frontend->+Backend:/taasjokurajapinta/
Backend->-Frontend:Onnistuminen
Frontend->-Käyttäjä:Onnistuminen