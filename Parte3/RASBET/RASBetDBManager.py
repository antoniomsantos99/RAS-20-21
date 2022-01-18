#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json
import requests
from requests.api import get
import mysql.connector
from dateutil import parser

futHeader = {
"x-apisports-key" : "6d3f4ef64e8c7e09d85d42639fe5ddf5"
}

tenHeader = {
    'x-rapidapi-host': "tennis-odds.p.rapidapi.com",
    'x-rapidapi-key': "5fadc4e78fmsh919151b7632eb4cp1dfbbcjsn2a2a80bb2ae1"
}

def getInfoTenis():
    params = {"page":"0","size":"50"}
    headers = {
        'x-rapidapi-host': "tennis-odds.p.rapidapi.com",
        'x-rapidapi-key': "5fadc4e78fmsh919151b7632eb4cp1dfbbcjsn2a2a80bb2ae1"
        }
    response = requests.request("GET", "https://tennis-odds.p.rapidapi.com/odds/prematch", headers=headers, params=params)
    return response.json()

def importJogosTenis():
    mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root"
    )
    mycursor = mydb.cursor()

    jogos=getInfoTenis()

    for jogo in jogos:
        localizacao = jogo["competition"]["country"]["name"]
        idj = 'T'+str(jogo['id'])
        resultado="(0)0-(0)0"

        
        odds=jogo["markets"][0]["bookmakers"][0]["odds"]["decimal"] if len(jogo["markets"])!= 0 else [0,0]
        
        data = parser.parse(jogo['startTime'])
        parts=[jogo["home"],jogo["away"]]

        for participante in parts:
             mycursor.execute(f'INSERT INTO rasbet.Participante (nome) SELECT * FROM (SELECT "{participante}") AS tmp WHERE NOT EXISTS (SELECT nome FROM rasbet.Participante WHERE nome = "{participante}") LIMIT 1')
        
        comp=jogo["competition"]
        #print(f'INSERT INTO rasbet.Competicao (id,nome) SELECT * FROM (SELECT "{"f"+str(comp["id"])}","{comp["name"]}") AS tmp WHERE NOT EXISTS (SELECT id FROM rasbet.Competicao WHERE id = "{"f"+str(comp["id"])}") LIMIT 1')
        mycursor.execute(f'INSERT INTO rasbet.Competicao (id,nome) SELECT * FROM (SELECT "{"t"+str(comp["id"])}","{comp["name"]}") AS tmp WHERE NOT EXISTS (SELECT id FROM rasbet.Competicao WHERE id = "{"t"+str(comp["id"])}") LIMIT 1')

        mycursor.execute(f'INSERT INTO rasbet.jogo (id, Competicao,Participante1,Participante2,Odd1,Odd2,Odd3,resultado,data,localizacao,estado) VALUES ("{idj}", "{"t"+str(comp["id"])}","{parts[0]}", "{parts[1]}","{odds[0]}", "{odds[1]}","{-1}", "{resultado}","{data}", "{localizacao}","{jogo["matchStatus"]}") ON DUPLICATE KEY UPDATE Odd1={odds[0]},Odd2={odds[1]},Odd3={-1},resultado="{resultado}"')

    mydb.commit()
    


def getOddsJogo(jsonOdds,id):
    for jogo in jsonOdds["response"]:
        #print(jogo["fixture"]["id"])
        if jogo["fixture"]["id"]==id:
            odds = jogo["bookmakers"][0]["bets"][0]
            return [float(x["odd"]) for x in odds["values"]]
    return [0.0,0.0,0.0]


def getJogosFutebol(season,liga):
    params = {
        'season' : season,
        'league': liga
    }
    #Pedir á API
    response = requests.get("https://v3.football.api-sports.io/fixtures",headers=futHeader,params=params)
    response.encoding='utf-8'
    return response.json()

def getOddsFutebol(season,liga):
    params = {
        'season' : season,
        'league': liga
    }
    #Pedir á API
    response = requests.get("https://v3.football.api-sports.io/odds",headers=futHeader,params=params)
    response.encoding='utf-8'
    return response.json()


def importJogosFutebol(jsonJogos,jsonOdds):
    mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root"
    )
    mycursor = mydb.cursor()

    for jogo in jsonJogos['response']:
        info = jogo['fixture']
        idj = 'F'+str(info['id'])
        localizacao = info["venue"]["name"]
        


        for participante in [jogo["teams"]["home"]["name"],jogo["teams"]["away"]["name"]]:
            
            #print(f'INSERT INTO rasbet.Participante (nome) SELECT * FROM (SELECT "{participante}") AS tmp WHERE NOT EXISTS (SELECT nome FROM rasbet.Participante WHERE nome = "{participante}") LIMIT 1')
            mycursor.execute(f'INSERT INTO rasbet.Participante (nome) SELECT * FROM (SELECT "{participante}") AS tmp WHERE NOT EXISTS (SELECT nome FROM rasbet.Participante WHERE nome = "{participante}") LIMIT 1')
        
        odds = getOddsJogo(jsonOdds,info['id'])

        comp=jogo["league"]
        #print(f'INSERT INTO rasbet.Competicao (id,nome) SELECT * FROM (SELECT "{"f"+str(comp["id"])}","{comp["name"]}") AS tmp WHERE NOT EXISTS (SELECT id FROM rasbet.Competicao WHERE id = "{"f"+str(comp["id"])}") LIMIT 1')
        mycursor.execute(f'INSERT INTO rasbet.Competicao (id,nome) SELECT * FROM (SELECT "{"f"+str(comp["id"])}","{comp["name"]}") AS tmp WHERE NOT EXISTS (SELECT id FROM rasbet.Competicao WHERE id = "{"f"+str(comp["id"])}") LIMIT 1')

        resultado = f"{0 if not jogo['goals']['home'] else jogo['goals']['home']}-{0 if not jogo['goals']['away'] else jogo['goals']['away']}"
        data = info['date']
        #print(f'INSERT INTO rasbet.jogo (id, Competicao,Participante1,Participante2,Odd1,Odd2,Odd3,resultado,data,localizacao,estado) VALUES ("{idj}", "{"f"+str(comp["id"])}","{jogo["teams"]["home"]["name"]}", "{jogo["teams"]["away"]["name"]}","{odds[0]}", "{odds[1]}","{odds[2]}", "{resultado}","{data}", "{localizacao}","{info["status"]["short"]}") ON DUPLICATE KEY UPDATE Odd1={odds[0]},Odd2={odds[1]},Odd3={odds[2]},resultado="{resultado}"')
        mycursor.execute(f'INSERT INTO rasbet.jogo (id, Competicao,Participante1,Participante2,Odd1,Odd2,Odd3,resultado,data,localizacao,estado) VALUES ("{idj}", "{"f"+str(comp["id"])}","{jogo["teams"]["home"]["name"]}", "{jogo["teams"]["away"]["name"]}","{odds[0]}", "{odds[1]}","{odds[2]}", "{resultado}","{data}", "{localizacao}","{info["status"]["short"]}") ON DUPLICATE KEY UPDATE Odd1={odds[0]},Odd2={odds[1]},Odd3={odds[2]},resultado="{resultado}"')

    mydb.commit()


def importLiga(idLiga,season):
    print("Sacando informação sobre os jogos")
    jsonJogos = getJogosFutebol(season,idLiga)
    print("Sacando informação sobre as odds")
    jsonOdds = getOddsFutebol(season,idLiga)
    print("Populando base de dados")
    importJogosFutebol(jsonJogos,jsonOdds)

def RASBet():
    ligas = {
        94 : "Primeira Liga",
        140 : "La Liga",
        39 : "Premier League",
        78  : "Bundesliga 1"
    }

    print("Começando futebol")
    for id in ligas:
        print(f"Começando {ligas[id]}")
        importLiga(id,2021)

    print("Começando ténis")
    importJogosTenis()

RASBet()
