package com.example.jasangovor.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

val firstDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "introduction",
            "title" to "Uvod",
            "solved" to false,
            "steps" to listOf(
                "Dobrodošli u 1. dan vašeg treninga govora",
                "Vodit ćemo vas korak po korak",
                "Upoznajmo se s planom treninga",
                "Kroz ovaj trening naučit ćete nekoliko govornih vježbi koje će vam pomoći u terapiji mucanja. Prvo ćete se upoznati s vježbama, zatim ćete ih redovito vježbati, a cilj je da ih primijenite u stvarnom životu, u komunikaciji s drugim ljudima. Plan je da dnevno vježbate otprilike 20 minuta."
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "learn",
            "title" to "Upoznavanje Tehnike Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Ova govorna tehnika vam može poboljšati fluentnost",
                "Zove se Tehnika Fleksibilne Brzine Govora",
                "Tehnika Fleksibilne Brzine Govora podrazumijeva usporavanje izgovora riječi, posebno prvog sloga. Usporavanje omogućuje mozgu planiranje, a govornim mišićima izvršavanje. To značajno smanjuje mucanje.",
                "Koraci:\n1. Stavite ruku ispod brade\n2. Izgovorite riječ\n3. Izbrojite koliko puta brada dodirne vašu ruku",
                "Primjer: Jabuka\nJa-bu-ka\nIma 3 sloga"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "exercise",
            "title" to "Vježba brojanja slogova",
            "solved" to false,
            "steps" to listOf(
                "Vrijeme je da primijenimo ono što smo upravo naučili",
                "Počnimo s vježbom brojanja slogova",
                "Odredite točan broj slogova za riječ prikazanu na ekranu",
                "jabuka",
                "ja-bu-ka\n3 sloga",
                "stolica",
                "sto-li-ca\n3 sloga",
                "prozor",
                "pro-zor\n2 sloga",
                "knjiga",
                "knji-ga\n2 sloga",
                "automobil",
                "au-to-mo-bil\n4 sloga",
                "računalo",
                "ra-ču-na-lo\n4 sloga",
                "matematika",
                "ma-te-ma-ti-ka\n5 slogova"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "learn",
            "title" to "Tehnika Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Sada kada smo upoznati sa slogovima",
                "Idemo se dublje upoznati s Tehnikom Fleksibilne Brzine Govora",
                "Sada kada znamo rastaviti riječ na slogove, potrebno je usporiti izgovor prvog sloga riječi. To ćemo postići na način da rastegnemo samoglasnik u prvom slogu",
                "Pogledajmo primjer",
                "Riječ: Prezentacija\npre-zen-ta-ci-ja\nRastegnuti ćemo samoglasnik u prvom slogu:\nPreeeeeee-zentacija"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "exercise",
            "title" to "Vježba Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Sada je vrijeme za vježbanje Fleksibilne Brzine Govora",
                "Izgovorite naglas riječ na zaslonu, istežući samoglasnik u prvom slogu istaknute riječi",
                "maaaaa-šina",
                "pooooo-luga",
                "suuuuun-ce",
                "vooooo-zilo",
                "raaaaz-red",
                "teeeee-lefon",
                "kaaaaa-mion"
            )
        ),
        "exercise_6" to hashMapOf(
            "id" to 6,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 1. dan treninga",
                "Razumjeli smo program i naučili prvu tehniku govora",
                "Pokušajte primijeniti Fleksibilnu Brzinu Govora u stvarnim razgovorima danas",
                "Vratite se na 2. dan gdje ćemo dublje istražiti uzroke mucanja i početi učiti novu moćnu tehniku"
            )
        )
    )
)
val secondDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "exercise",
            "title" to "Vježba zagrijavanja",
            "solved" to false,
            "steps" to listOf(
                "Što kažete da malo zagrijemo naše govorne mišiće?",
                "Izgovorite naglas slova koji se pojavljuju na zaslonu",
                "Slovo: L",
                "Brzo!",
                "L",
                "L L",
                "L L L",
                "Sporo!",
                "L L",
                "L L L",
                "Slovo: R",
                "Brzo!",
                "R",
                "R R R",
                "R R",
                "Sporo!",
                "R",
                "R R R",
                "R R",
                "Slovo: B",
                "Brzo!",
                "B B",
                "B B B",
                "B",
                "Sporo!",
                "B B B",
                "B B"
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "learn",
            "title" to "Upute za Tehniku Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Jučer smo naučili tehniku Fleksibilne brzine govora",
                "Evo nekoliko uputa za korištenje Tehnike Fleksibilne Brzine Govora",
                "Možete primjetiti da ova tehnika zvuči drugačije nego normalan govor",
                "Cilj je koristiti tehniku Fleksibilne Brzine Govora na način da ona bude kratka i neprimjetna",
                "Krajni cilj nam je da nam govor zvuči što prirodnije moguće",
                "Primjetimo da je ova tehnika drugačija od usporavanja brzine govora.\nOvdje usporavamo izgovor dijela riječi gdje očekujemo pojavu mucanja, a to je prvi slog u riječi",
                "Kako bi usavršili ovu tehniku morate vježbati što je više moguće.\nNemojte vježbati samo u ovoj aplikaciji, vježbajte u stvarnim razgovorima s ljudima",
                "Nemojte očekivati brze rezultate, usavršavanje ove tehnike treba vremena.\nUpornost je od najveće važnosti!"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "learn",
            "title" to "Razumijevanje mucanja",
            "solved" to false,
            "steps" to listOf(
                "Prije nego što prijeđemo na tehniku Izvlačenja, prvo identificirajmo različite vrste mucanja",
                "Nakon što ih upoznate, bit ćete bolje pripremljeni za saznanje što se događa kada mucate",
                "Većina ljudi ili nezna za svoje mucanje ili ga ne žele prihvatiti.\nNo kada prihvatite svoje mucanje biti že vam manje neugodno kada se ono pojavi",
                "Postoje tri ponašanja povezana s mucanjem:\n1. Temeljna ponašanja\n2. Sekundarna ponašanja\n3. Osjećaji i stavovi povezani s vašim mucanjem",
                "Naučimo nešto o Temeljnim ponašanjima",
                "Sastoje se od:\n1. Ponavljanja\n2. Produživanja\n3. Blokiranja",
                "Ponavljanje je kada ponavljanje prvi glas riječi\nPrimjer: t..t..t..tata",
                "Produživanje je kada produljite prvi glas riječi:\nPrimjer: ttttttata",
                "Blokiranje je kao da vas nešto spriječi u izgovaranju riječi.\nIzgovorite riječ koja ne zvuči kao riječ koju ste htjeli izgovoriti.",
                "Naučimo nešto o Sekundarnim ponašanjima",
                "Sekundarna ponašanja su trikovi koje vi koristite kako bi izbjegli pojavu mucanja.\nSastoje se od:\n1. Izbjegavanja\n2. Bježanja",
                "Izbjegavanje je kada zatražite drugu osobu da umjesto vas naruči hranu",
                "Bježanje je kompletno izbjegavanje situacija gdje se može dogoditi pojava mucanja",
                "Naučimo nešto o osjećajima i stavovima",
                "Način na koji se osjećate prema mucanju može poboljšati ili pogoršati vašu tečnost",
                "Ako vas je strah mucanja vaši govorni mišići će biti napeti što će prouzrokovati više mucanja",
                "Najčešći problem kod mucanja je strah.\nUkoliko pobijedite vaš strah od mucanja napraviti ćete ogroman napredak u vašem govoru"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "exercise",
            "title" to "Vježba Fleksibilne Brzine govora",
            "solved" to false,
            "steps" to listOf(
                "Sada je vrijeme za malo vježbanja fleksibilne brzine govora",
                "Izgovorite naglas riječ na zaslonu, istežući samoglasnik u prvom slogu istaknute riječi",
                "leeee-ti",
                "aaaaa-narhija",
                "deeeee-bata",
                "raaaaak",
                "doooo-govor",
                "pooooo-milovanje",
                "biiiiii-ografija",
                "gaaaaa-lama",
                "miiiiiir-noća",
                "zaaaaaa-hvalnost"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 6,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 2. dan treninga",
                "Naučili smo nešto novo o mucanju i usavršavali Tehniku Fleksibilne Brzine Govora",
                "Pokušajte primjeniti navedenu tehniku u vašim razgovorima danas",
                "Vratite se na 3. dan gdje ćemo dublje istražiti uzroke mucanja i početi učiti novu moćnu tehniku"
            )
        )
    )
)
val thirdDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "quiz",
            "title" to "Kviz",
            "solved" to false,
            "steps" to listOf(
                "Prije nego što nastavimo, riješimo kratki kviz kako bi se prisjetili jučerašnje lekcije",
                "Od navedenih stvari koje NISU ponašanja mucanja?\nNapetost\nPrimarna ponašanja\nSekundarna ponašanja",
                "Odgovor:\nNapetost\nPrimarna ponašanja",
                "Od navedenih ponašanja koja NISU temeljna ponašanja?\nBlokovi\nLjutnja\nSram",
                "Odgovor:\nLjutnja\nSram",
                "Najčešći problem kod mucanja je?\nStrah\nili\nLjutnja",
                "Odgovor:\nStrah"
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "learn",
            "title" to "Tehnika Izlaska",
            "solved" to false,
            "steps" to listOf(
                "Naučit ćemo Tehniku Izlaska",
                "Sastoji se od 3 koraka, danas ćemo proći kroz prvi korak",
                "Što radite kada ste u trenutku mucanja?",
                "Možda pokušavate zaustaviti trenutak mucanja koristeći ponašanja bježanja poput trzanja glave ili pokrete glavom",
                "Najčešće se dogodi da se bespomoćno mučite prestati mucati",
                "Promijeniti ćemo ovo ponašanje koristeći Tehniku Izlaska iz Blokade",
                "Tehnika Izlaska sastoji se od 3 koraka:\nHvatanje\nZadržavanje\nOtpuštanje"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "learn",
            "title" to "Korak Hvatanja",
            "solved" to false,
            "steps" to listOf(
                "Prvi korak Tehnike Izlaska je Hvatanje",
                "Hvatanje uključuje prepoznavanje trenutka mucanja u trenutku kada se ono događa",
                "Primjer:\nJa sam pilot\nJa sam p..p..p\nOvdje smo se uhvatili u trenutku mucanja",
                "Kako bi vježbali korak hvatanja namjerno ćemo mucati.\nMucanje treba izgledati što sličnije vašem stvarnom mucanju.",
                "Sada pokušajte namjerno zamucati na riječ pilot i oponašajte vaše stvarno mucanje"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "exercise",
            "title" to "Vježba Hvatanja",
            "solved" to false,
            "steps" to listOf(
                "Sada ćemo malo vježbati hvatanje",
                "Namjerno zamucajte na istaknutim riječima na zaslonu i uhvatite ih",
                "n..n..n..napitak",
                "k..k..k..konj",
                "p..p..p..potok",
                "Z..Z..Z..Zagreb",
                "l..l..l..lonac",
                "k..k..k..kuhalo",
                "b..b..b..batak"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "exercise",
            "title" to "Vježba Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Sada je vrijeme za malo vježbanja fleksibilne brzine govora",
                "Izgovorite naglas riječ na zaslonu, istežući samoglasnik u prvom slogu istaknute riječi",
                "laaaa-vanda",
                "priiii-badaća",
                "poooo-mazati",
                "zlaaaa-to",
                "kooo-lač",
                "doooo-brodošli",
                "zaaaaa-povijedati",
                "eeeee-lektrični",
                "pooo-zdraviti",
                "puuuu-tovanje"
            )
        ),
        "exercise_6" to hashMapOf(
            "id" to 6,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 3. dan treninga",
                "Počeli smo učiti o Tehnici Izlaska",
                "Danas pokušajte uhvatiti svoje mucanje kada budete vodili razgovore s ljudima",
                "Vratite se na 4. dan gdje ćemo nastaviti učenje Tehnike Izlaska"
            )
        )
    )
)
val fourthDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "quiz",
            "title" to "Kviz",
            "solved" to false,
            "steps" to listOf(
                "Prije nego što nastavimo, riješimo kratki kviz kako bi se prisjetili jučerašnje lekcije",
                "Koja su 3 koraka kod Tehnike Izlaska?\nHvatanje\nLiječenje\nDržanje\nOslobađanje",
                "Odgovor:\nHvatanje\nZadržavanje\nOtpuštanje",
                "Korak Hvatanja kod Tehnike Izlaska je označavao?\nPrepoznavanje trenutka mucanja\nPokušaj zaustavljanja mucanja",
                "Odgovor:\nPrepoznavanje trenutka mucanja",
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "exercise",
            "title" to "Vježba zagrijavanja",
            "solved" to false,
            "steps" to listOf(
                "Što kažete da malo zagrijemo naše govorne mišiće?",
                "Izgovorite naglas slova koji se pojavljuju na zaslonu",
                "Slovo: J",
                "Brzo!",
                "J",
                "J J",
                "J J J",
                "Sporo!",
                "J J",
                "J J J",
                "Slovo T",
                "Brzo!",
                "T",
                "T T T",
                "T T",
                "Sporo!",
                "T",
                "T T T",
                "T T",
                "Slovo: E",
                "Brzo!",
                "E E",
                "E E E",
                "E",
                "Sporo!",
                "E E E",
                "E E"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "learn",
            "title" to "Korak Zadržavanja",
            "solved" to false,
            "steps" to listOf(
                "Drugi korak Tehnike Izlaska je Zadržavanje",
                "Zadržavanje uključuje držanje trenutka mucanja, ne pokušaj bijega od njega",
                "Cilj zadržavanja je osjetiti na kojim točkama artikulatora osjećate napetost: jezik, usne, čeljust...",
                "Na primjer kod glasa P može doći do napetosti na usnama, no moguće je osjećati napetost u čeljusti, zubima ili jeziku",
                "Primjer:\nNamjerno ćemo zamucati na riječi pilot.\np..p..p..p\nPromatrati ćemo napetost na usnama",
                "Sada pokušajte vi namjerno mucati dok ne osjetite napetost i promatrajte ju\np..p..p..p..p..p"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "exercise",
            "title" to "Vježba Prepoznavanja Napetosti",
            "solved" to false,
            "steps" to listOf(
                "Vježbajmo prepoznavanje napetosti na različitim riječima",
                "a..a..a..a..a..ambicija",
                "p..p..p..p..p..posudba",
                "s..s..s..s..s..sličnost",
                "u..u..u..u..u..ukusno",
                "k..k..k..k..k..kamen",
                "b..b..b..b..b..bus"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "exercise",
            "title" to "Vježba Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Sada je vrijeme za malo vježbanja fleksibilne brzine govora",
                "Izgovorite naglas riječ na zaslonu, istežući samoglasnik u prvom slogu istaknute riječi",
                "aaaaaam-bicija",
                "oooooo-bjekt",
                "koooooo-loseum",
                "skaaaaa-kanje",
                "gooooor-čina",
                "duuuuuuug",
                "biiiiii-lježnica",
                "aaaaaaar-tefakt",
                "pooooo-gođen",
                "pčeeeee-la",
                "voooooo-dopad"
            )
        ),
        "exercise_6" to hashMapOf(
            "id" to 6,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 4. dan treninga",
                "Naučili smo 2. korak Tehnike Izlaska",
                "Danas pokušajte prepoznati napetost kod mucanja kada budete vodili razgovore s ljudima",
                "Vratite se na 5. dan gdje ćemo završiti učenje Tehnike Izlaska"
            )
        )
    )
)
val fifthDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "quiz",
            "title" to "Kviz",
            "solved" to false,
            "steps" to listOf(
                "Prije nego što nastavimo, riješimo kratki kviz kako bi se prisjetili jučerašnje lekcije",
                "Od navedenih ponašanja koja NISU temeljna ponašanja?\nBlokovi\nLjutnja\nSram",
                "Odgovor:\nLjutnja\nSram",
                "Prva dva koraka Tehnike Izlaska su:\nHvatanje i Napetost\nHvatanje i Zadržavanje\nDržanje i Otpuštanje",
                "Odgovor:\nHvatanje i Zadržavanje"
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "learn",
            "title" to "Korak Otpuštanja",
            "solved" to false,
            "steps" to listOf(
                "Naučili smo korake Hvatanja i Zadržavanja",
                "Hvatanje je uključivalo prepoznavanje trenutka mucanja u trenutku kada se ono događa",
                "Zadržavanje je uključivalo zadržavanje trenutka mucanja kako bi se prepoznala napetost u artikulatorima",
                "Naučimo 3. korak:\nOtpuštanje",
                "Kod Otpuštanja nastavljamo mucanje ali cilj nam je smanjivanje napetosti mišića koju osjetimo u trenutku mucanja",
                "Izgovor riječi zaustavljamo tek kada smo uspješno uklonili napetost mišića koju smo osjećali",
                "Primjer:\np..p..p..p..p..p..pilot\nZaustaviti ćemo izgovor riječi tek kada smo uklonili napetost usana"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "learn",
            "title" to "Sažetak Tehnike Izlaska",
            "solved" to false,
            "steps" to listOf(
                "Koraci:",
                "1. uhvati trenutak mucanja",
                "2. zadrži mucanje i odredi napetost artikulatora",
                "3. otpusti napetost i završi riječ bez napetosti",
                "Tehnika izlaska se može koristiti za ponavljanja, blokade i produžanja",
                "Sada je red na vama da primjenite ove korake u razgovorima danas"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "exercise",
            "title" to "Vježba Tehnike Izlaska",
            "solved" to false,
            "steps" to listOf(
                "Izgovorite naglas riječ koja se pojavljuje na zaslonu, primjenjujući Tehniku Izvlačenja na prvo slovo riječi",
                "dječak",
                "interakcija",
                "jakna",
                "obrazovanje",
                "zastarjeo",
                "avion",
                "patka",
                "bunar",
                "ukusno",
                "plivati",
                "trčati",
                "slatko"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "learn",
            "title" to "Savjeti i upute za Tehnku Izvlaćenja",
            "solved" to false,
            "steps" to listOf(
                "Jako je važno vježbati ovu tehniku, ne samo u aplikaciji, već i u stvarnom životu",
                "Kada vježbate u stvarnom životu, shvatit ćete prave nijanse ove tehnike",
                "U početku ova tehnika će zvučati jako neprirodno i to je sasvim normalno, s vježbanjem i vremenom zvučati će normalnije",
                "Sada kada ste sami izvježbali tehniku, potrebno ju je primjeniti na ostale okolnosti",
                "Koristite tehniku u razgovoru s bliskim prijateljem",
                "kada ste to dobro izvježbali, koristite tehniku u razgovoru s obitelji",
                "Zadatak vam je da si postepeno otežavate okolnosti i tako vježbate"
            )
        ),
        "exercise_6" to hashMapOf(
            "id" to 6,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 5. dan treninga",
                "Završili smo Tehniku Izvlačenja",
                "Nemojte zaboraviti primjenjivati ovu tehniku u svakodnevnim razgovorima",
                "Jedino s vježbanjem možete postići napredak"
            )
        )
    )
)
val sixthDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "quiz",
            "title" to "Kviz",
            "solved" to false,
            "steps" to listOf(
                "Prije nego što nastavimo, riješimo kratki kviz kako bi se prisjetili jučerašnje lekcije",
                "Koji je 3. korak Tehnike Izlaska?\nHvatanje\nZadržavanje\nLiječenje\nOtpuštanje",
                "Odgovor:\nOtpuštanje",
                "Gdje se treba vježbati Tehnika Izlaska?\nU aplikaciji\nU stvarnom životu",
                "Odgovor:\nU aplikaciji\nU stvarnom životu"
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "exercise",
            "title" to "Vježba zagrijavanja",
            "solved" to false,
            "steps" to listOf(
                "Što kažete da malo zagrijemo naše govorne mišiće?",
                "Izgovorite naglas slova koji se pojavljuju na zaslonu",
                "Slovo: L",
                "Brzo!",
                "L",
                "L L",
                "L L L",
                "Sporo!",
                "L L",
                "L L L",
                "Slovo T",
                "Brzo!",
                "T",
                "T T T",
                "T T",
                "Sporo!",
                "T",
                "T T T",
                "T T",
                "Slovo: P",
                "Brzo!",
                "P",
                "P P P",
                "P P",
                "Sporo!",
                "P P P",
                "P P"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "exercise",
            "title" to "Vježba Prepoznavanja Napetosti",
            "solved" to false,
            "steps" to listOf(
                "Vježbajmo prepoznavanje napetosti na različitim riječima",
                "p..p..p..p..p..potok",
                "k..k..k..k..k..kamilica",
                "t..t..t..t..t..tramvaj",
                "u..u..u..u..u..udariti",
                "b..b..b..b..b..banana",
                "l..l..l..l..l..lopata"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "exercise",
            "title" to "Vježba Tehnike Izlaska",
            "solved" to false,
            "steps" to listOf(
                "Vježbati ćemo Tehniku Izlaska",
                "Zapamtite, bez vježbanja ove tehnike nisu od pomoći",
                "Izgovorite naglas riječ koja se pojavljuje na zaslonu, primjenjujući Tehniku Izvlačenja na prvo slovo riječi",
                "to",
                "posao",
                "emocije",
                "strah",
                "prati",
                "ubrojiti",
                "tvornica",
                "izlaziti",
                "vodopad"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "reading",
            "title" to "Čitanje",
            "solved" to false,
            "steps" to listOf(
                "Idemo sada malo vježbati čitanje",
                "Dok čitate, pokušajte primjeniti Tehniku Izlaska",
                "Trudite se čitati polako, smireno, jasno i razgovjetno",
                "Gravitacija\n\nGravitacija je sila privlačenja između svih masa u svemiru. Ova sila drži planete u orbitama oko Sunca i omogućuje da objekti padaju na Zemlju. Newtonov zakon gravitacije opisuje kako se sila smanjuje s udaljenošću između tijela. Einsteinova teorija relativnosti dodatno objašnjava gravitaciju kao zakrivljenost prostora i vremena. Gravitacija je jedna od temeljnih sila prirode.",
                "Ljepota godišnjih doba\n\nSvako godišnje doba donosi posebne čari i mijenja prirodu oko nas. Proljeće je vrijeme buđenja, kad cvijeće cvjeta, drveće se zeleni, a dani postaju topliji. Ljudi više borave vani i sade vrtove. Ljeto donosi sunce, visoke temperature i praznike. Djeca uživaju na moru ili u planinama, a večeri su idealne za druženje. Jesen je poznata po šarenom lišću, berbi i mirisu kestena. Dani su kraći i svježiji, što je odlično za šetnje. Zima donosi snijeg i blagdane. Djeca se sanjkaju, a obitelji provode više vremena zajedno. Svako doba ima svoju ljepotu i podsjeća nas da uživamo u svakom trenutku."
            )
        ),
        "exercise_6" to hashMapOf(
            "id" to 6,
            "type" to "exercise",
            "title" to "Vježba Fleksibilne Brzine Govora",
            "solved" to false,
            "steps" to listOf(
                "Završimo današnji trening s malo vježbanja Fleksibilne Brzine Govora",
                "Izgovorite naglas riječ na zaslonu, istežući samoglasnik u prvom slogu istaknute riječi",
                "ooooooo-dlično",
                "kraaaaa-tak",
                "diiiiii-jete",
                "ooooooo-dmor",
                "preeeee-teći",
                "suuuuuun-ce",
                "šaaaaaa-lica",
                "paaaaaa-pir"
            )
        ),
        "exercise_7" to hashMapOf(
            "id" to 7,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 6. dan treninga",
                "Večinu današnjeg treninga proveli ste vježbajući Tehniku Izlaska",
                "Vjerojatno ste nestrpljivi da naučite nove tehnike, no vježbanje i usavršavanje starih tehnika je od iznimne važnosti"
            )
        )
    )
)
val seventhDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "quiz",
            "title" to "Na Pravom Putu?",
            "solved" to false,
            "steps" to listOf(
                "Prije nego što nastavimo, riješimo kratki kviz kako bi provjerili jeste li na pravom putu",
                "Vježbate li Tehniku Izlaska u svakodnevnim razgovorima?",
                "Nije dovoljno vježbanje samo u aplikaciji, pravi napredak ćete vidjeti ako vježbate tehnike u svakodnevnim razgovorima",
                "Kod Tehnike Fleksibilne Brzine Govora što istežemo u prvom slogu riječi?\nSamoglasnik\nSuglasnik",
                "Odgovor\nSamoglasnik"
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "exercise",
            "title" to "Vježba Prepoznavanja Napetosti",
            "solved" to false,
            "steps" to listOf(
                "Vježbajmo prepoznavanje napetosti na različitim riječima",
                "p..p..p..p..p..palača",
                "t..t..t..t..t..telefon",
                "b..b..b..b..b..brdo",
                "d..d..d..d..d..drvo",
                "s..s..s..s..s..sladoled",
                "m..m..m..m..m..majka"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "exercise",
            "title" to "Vježba Tehnike Izlaska",
            "solved" to false,
            "steps" to listOf(
                "Ponovno ćemo vježbati Tehniku Izlaska",
                "Zapamtite, bez vježbanja ove tehnike nisu od pomoći",
                "Izgovorite naglas riječ koja se pojavljuje na zaslonu, primjenjujući Tehniku Izvlačenja na prvo slovo riječi",
                "psihologija",
                "skupocjeno",
                "artikl",
                "odobriti",
                "izbjegavati",
                "sličan",
                "dosadan",
                "univerzalan",
                "šala"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "learn",
            "title" to "Strašne riječi",
            "solved" to false,
            "steps" to listOf(
                "Naučimo ponešto o Strašnim Riječima",
                "Možda ste sami primjetili da ne zamucate zbog specifičnih riječi nego zbog specifičnih glasova",
                "To mogu biti glasovi poput:\nP\nV\nB\nT\nS\nJ\n...",
                "Možda se kod vas mucanje događa jako rijetko ali se uvijek dogodi na nekoj specifičnoj riječi",
                "Posjetite dio sa Strašnim Glasovima u aplikaciji"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 7. dan treninga",
                "Možda vam je bilo dosadno ponovno vježbati Tehniku Izlaska",
                "Ali bez svakodnevne vježbe ne možete usavršiti tehniku",
                "Sutra ćemo ponovno vježbati Tehniku Izlaska!"
            )
        )
    )
)
val eightDay = hashMapOf(
    "daySolved" to false,
    "exercises" to hashMapOf(
        "exercise_1" to hashMapOf(
            "id" to 1,
            "type" to "introduction",
            "title" to "Pregled dana",
            "solved" to false,
            "steps" to listOf(
                "Dobrodošli na 8. dan",
                "Danas ćemo vježbati Tehniku Izlaska i naučiti ćemo novu tehniku"
            )
        ),
        "exercise_2" to hashMapOf(
            "id" to 2,
            "type" to "exercise",
            "title" to "Vježba zagrijavanja",
            "solved" to false,
            "steps" to listOf(
                "Što kažete da malo zagrijemo naše govorne mišiće?",
                "Izgovorite naglas slova koji se pojavljuju na zaslonu",
                "Slovo: S",
                "Brzo!",
                "S",
                "S S",
                "S S S",
                "Sporo!",
                "S S",
                "S S S",
                "Slovo I",
                "Brzo!",
                "I",
                "I I I",
                "I I",
                "Sporo!",
                "I",
                "I I I",
                "I I",
                "Slovo: C",
                "Brzo!",
                "C",
                "C C C",
                "C C",
                "Sporo!",
                "C C C",
                "C C"
            )
        ),
        "exercise_3" to hashMapOf(
            "id" to 3,
            "type" to "exercise",
            "title" to "Vježba Tehnike Izlaska",
            "solved" to false,
            "steps" to listOf(
                "Ponovno ćemo vježbati Tehniku Izlaska",
                "Zapamtite, bez vježbanja ove tehnike nisu od pomoći",
                "Izgovorite naglas riječ koja se pojavljuje na zaslonu, primjenjujući Tehniku Izvlačenja na prvo slovo riječi",
                "zdjela",
                "literatura",
                "ručnik",
                "pistacija",
                "vidljiv",
                "čitanje",
                "odlazak",
                "blagoslov",
                "šala"
            )
        ),
        "exercise_4" to hashMapOf(
            "id" to 4,
            "type" to "learn",
            "title" to "Pripremni Postupci",
            "solved" to false,
            "steps" to listOf(
                "Danas ćemo napokon naučiti novu tehniku",
                "Uzbuđeni ste zar ne?",
                "Tehnika se zove Pripremni Postupci",
                "Sve osobe koje mucaju uglavnom očekuju riječi na kojima će mucati",
                "Te riječi će onda ili zamjeniti nekom drugom lakšom riječi ili će se uplašiti te riječi i njihovi će mišići postati napeti što će uzrokovati još mucanja",
                "Ukratko, očekivanje mucanja najčešće dovodi do povečanog mucanja",
                "Pripremne vježbe su slične Tehnici Izlaska, no one uključuju radnje prije izgovora riječi",
                "Koraci su:\n1. Pauzirajte prije izgovora riječi\n2. Opustite vaše govorne mišiće\n3. Sjetite se čestih grešaka koje napravite kod izgovora te riječi\n4. Prisjetite se podrućja koja ste opustili\n5. Zamišljajte kako ćete ispravno izgovoriti riječ\n6. Izgovorite riječ",
                "Ova vježba je zahtjevna i sadrži mnogo koraka, ali jednom kada je svladana donijeti će vam puno uspjeha u vašem govoru",
                "Primjer:\nMogu li vas nešto pitati?\nOčekujemo da ćemo zamucati na riječ \"pitanje\"\nKod izgovora glasa \"p\" napeti će se usne i zamucati ćemo",
                "Napraviti ćemo sledeće korake:\n1. Napraviti ćemo pauzu prije riječi \"pitanje\"\n2. Opustiti ćemo naše usne\n3. Prisjetiti ćemo se da brzo mrdamo svojim usnama kod riječi \"pitanjw\"\n4. I dalje ćemo opuštati usne\n5. Zamišljati ćemo pravilan izgovor riječi\n6. Izgovoriti ćemo riječ",
                "Sada pokušajte sami"
            )
        ),
        "exercise_5" to hashMapOf(
            "id" to 5,
            "type" to "learn",
            "title" to "Vježba Priprenih Postupaka",
            "solved" to false,
            "steps" to listOf(
                "Pripremite se za malo vježbanja Pripremih Postupaka",
                "Izgovorite naglas riječ koja se pojavljuje na zaslonu, primjenjujući Pripremne Postupke",
                "zraka",
                "potok",
                "balon",
                "događaj",
                "dama",
                "majka",
                "sunce",
                "brat",
                "pas",
                "kutija",
                "tata",
                "slika"
            )
        ),
        "exercise_6" to hashMapOf(
            "id" to 6,
            "type" to "conclusion",
            "title" to "Zaključak",
            "solved" to false,
            "steps" to listOf(
                "Ovim završava vaš 8. dan treninga i stigli smo do kraja vašeg treninga",
                "Prisjetite se svih tehnika koje smo naučili i vježbajte ih svakodnevno u razgovorima",
                "Bez svakodnevne vježbe ne možete usavršiti tehniku",
                "Nemojte odustajati, uspjeh je iza ugla!",
                "Nastavite koristiti aplikaciju, vježbajte svakodnevno i uspjeh je zagarantiran!"
            )
        )
    )
)

fun initializeUsersDatabase() {
    val db = Firebase.firestore
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid ?: return

    val userData = hashMapOf(
        "name" to "Test",        // TODO: add user's name 
        "surname" to "User",     // TODO: add user's surname
        "email" to user.email
    )

    val days = listOf(
        "day_1" to firstDay,
        "day_2" to secondDay,
        "day_3" to thirdDay,
        "day_4" to fourthDay,
        "day_5" to fifthDay,
        "day_6" to sixthDay,
        "day_7" to seventhDay,
        "day_8" to eightDay
    )

    val batch = db.batch()
    val userRef = db.collection("users").document(uid)

    batch.set(userRef, userData)

    for ((docId, dayData) in days) {
        val dayRef = userRef.collection("dailyExercises").document(docId)
        batch.set(dayRef, dayData)
    }

    batch.commit()
        .addOnSuccessListener { Log.d("Firestore", "User and all days added!") }
        .addOnFailureListener { e -> Log.e("Firestore", "Batch add failed", e) }
}

