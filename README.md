# Energy-System

  **Logica si Functionalitatile Proiectului**

Pentru acest proiect am creat cateva pachete in care am separat logica in:

- input = Am creat clasele necesare parsarii inputului. Astfel trecerea datelor din fisierele JSON se va face foarte simplu in structurile create.

- output = Similar abordarii de mai sus, am creat clasele care vor contine informatia extrasa din implementarea interna, datele urmand sa fie duse in fisierele JSON de iesire.

- game = Contine implementarea proiectului prin simularea unui joc.
    * Game = clasa va tine minte consumatorii, distribuitorii, contractele si va rula runda cu runda jocul. Descrierea detaliata a flow-ului se afla mai jos.
    * Gamestate = clasa va contine informatiile necesare afisarii rezultatelor simularii. Aceasta va contine instante ale claselor din package-ul output

- entities = Contine clasele Consumer, Contract si Distributor.
    * Consumer va memora datele oferite in input si va crea consumatorii. In aceasta clasa vom pastra bugetul si un flag care sa ne spuna daca este sau nu falimentat.
    * Distributor va memora datele despre distribuitori, alaturi de functiile necesare calcularii ratelor lunare in functie de numarul de utilizatori raportati de Game
    * Contract va face legatura dintre un Distributor si o lista de Consumers. Daca intr-o anumita runda distribuitorul nu are clienti, acesta va avea un contract gol. Contractul va memora valoarea ratei lunare pe care consumatorii din contract o vor plati distribuitorului.

- factories = Creeaza instantele necesare in functie de datele primite.


     **Flow-ul Proiectului** 

    Dupa ce datele au fost trecute din JSON in clasele de input, un nou Game este creat cu ajutorul acestora.

    Clasa Game va simula jocul prin functia runGame. Aceasta functie va primi informatiile legate de update-urile efectuate in fiecare luna, care vor fi aplicate incepand cu runda 1.
    La inceputul unei luni se vor sterge din contracte distribuitorii si consumatorii care au falimentat. Dupa aceea, consumatorii isi vor primi salariile si vor semna noi contracte, in cazul in care contractul anterior a fost terminat din orice fel de motiv. Toti consumatorii vor semna un contract cu distribuitorul care ofera cel mai bun pret. Acesta va fi cautat inainte de a semna contractele noi.
    In finalul lunii, distribuitorii vor plati utilitatiile pentru toti consumatorii pe care ii au in contractele active in luna curenta (sau costul infrastructurii daca nu au consumatori). Acestia incaseaza ulterior banii din partea consumatorilor. Plata consumatorilor este descrise mai sus, in - entities.Consumer
    Dupa aceste 2 plati, se vor declara falimentare firmele de distribuire care au un buget negativ.

    La finalul simularii, Main-ul va cere Game-ului un gamestate in care se afla toate datele relevante pentru crearea output-ului in forma JSON.
