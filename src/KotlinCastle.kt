import kotlin.system.exitProcess

object KotlinMessages {
    val WELCOME = "Welcome to Kotlin Castle Mortal!"
    val OBJETIVO =
        "You will have the almost impossible challenge of rescuing the great crown of Sensato! Good luck Mortal!"
    val SAIR = "You're feeble"
}
var x:Int = 2 //posicao X do jogador
var y:Int = 3 //posicao Y do jogador
const val ALTURA = 4
const val LARGURA = 4
var tabuleiro = Tabuleiro()
var carga:MutableList<Objeto> = mutableListOf()
var state = true
var goblin = true


class Posicao(descricao_:String,
              _norte:Boolean = false,
              _sul:Boolean = false,
              _leste:Boolean = false,
              _oeste:Boolean = false){
    var descricao = descricao_
    var norte = _norte
    var leste = _leste
    var oeste = _oeste
    var sul = _sul
    var objectList:MutableList<Objeto?> = mutableListOf()
    fun addObjeto(objeto:Objeto){
        objectList.add(objeto)
    }
}

open abstract class Objeto(){
     open var descricao:String = "" //variável deve ser aberta e instanciada
     open var picked:Boolean = false
}

class Vela(): Objeto(){
     override var descricao:String = "Uma vela acesa no chão, perfeito para iluminar corredores escuros"
     override var picked:Boolean = false
}
class Chave():Objeto(){
    override var descricao:String = "Uma chave que te dá acesso à sala do trono"
    override var picked:Boolean = false
}
class Bau():Objeto(){
    override var descricao:String = "Um Bau fechado, será que devemos abrir-lo?"
    override var picked:Boolean = false

}
class Espada():Objeto(){
    override var descricao:String = "Uma espada, perfeita para matar Goblins"
    override var picked:Boolean = false


}
class Cafe():Objeto(){
    override var descricao:String = "Uma xícara de café doce, perfeita para Goblins raivosos"
    override var picked:Boolean = false

}
class Coroa():Objeto(){
    override var descricao:String = "O item mais desejado, a Coroa do Grande Sensato!"
    override var picked:Boolean = false

}
class Acucar():Objeto(){
    override var descricao:String = "O item mais desejado, a Coroa do Grande Sensato!"
    override var picked:Boolean = false

}



val vela:Vela = Vela()
val chave:Chave = Chave()
val cafe:Cafe = Cafe()
val espada:Espada = Espada()
val coroa:Coroa = Coroa()
val bau:Bau = Bau()
val acucar:Acucar = Acucar()

class Tabuleiro {
    var tabuleiro = arrayOf<Array<Posicao>>(arrayOf<Posicao>(Posicao("Você está na cozinha", _sul = true), Posicao("Você está na cozinha", _sul = true, _leste = true), Posicao("Você está no corredor interno", _oeste = true, _sul = true), Posicao("Você está na sala do trono", _sul = true)),
                                            arrayOf<Posicao>(Posicao("Você está na cozinha", _leste = true, _norte = true), Posicao("Você está na cozinha", _oeste = true, _norte = true), Posicao("Você está no corredor interno", _norte = true, _sul = true), Posicao("Você está na sala do trono. Tem um Goblin que gosta de café doce aqui...", _norte = true, _sul = true)),
                                            arrayOf<Posicao>(Posicao("Você está no quarto escuro", _sul = true, _leste = true), Posicao("Você está no corredor interno", _leste = true, _oeste = true, _sul = true), Posicao("Você está no corredor interno", _norte = true, _sul = true, _leste = true, _oeste = true), Posicao("Você está no corredor interno", _oeste = true, _sul = true, _norte = true)),
                                            arrayOf<Posicao>(Posicao("Você está no jardim", _norte = true), Posicao("Você está no corredor interno, tem uma vela aqui", _norte = true), Posicao("Você está na entrada principal", _norte = true), Posicao("Você está no corredor interno, tem um baú aqui", _norte = true))
    )
    fun getPosicao(x:Int, y:Int):Posicao {
        return tabuleiro[y][x]
    }
}
fun fillPosicao(){
    tabuleiro.getPosicao(0, 3).addObjeto(chave)
    tabuleiro.getPosicao(1, 3).addObjeto(vela)
    tabuleiro.getPosicao(3, 3).addObjeto(bau)
    tabuleiro.getPosicao(0, 1).addObjeto(espada)
    tabuleiro.getPosicao(0, 0).addObjeto(cafe)
    tabuleiro.getPosicao(3, 0).addObjeto(coroa)
}
//function to take object out of the board
fun retirarObjTab(obj:Objeto){
    tabuleiro.getPosicao(x, y).objectList.remove(obj)
}
//Ends the game based on outcome True = good False = bad
fun fim(rs:Boolean){
    if(rs){
        println("Meu parabéns, o rei Sensato de presenteou com um castelo magnífico e você vive tranquilamente até a próxima ameaça chegar...")
    }
    else{
        println("Você morreu dentro do castelo, esquecido por aqueles que um dia acreditaram em você")
    }
    state = false
}
//Direction interpreter
fun ir(palavras: List<String>){
    if("NORTE" in palavras && y > 0 && tabuleiro.getPosicao(x, y).norte) {
        if(x == 3 && y == 1 && goblin){
            println("O Goblin não deixa você sair!!!")
            return
        }
        y --
        println("Foi para o norte")
    }
    else if("SUL" in palavras && y < ALTURA - 1 && tabuleiro.getPosicao(x, y).sul) {
        y ++
        println("Foi para o sul")
    }
    else if("LESTE" in palavras && x < LARGURA - 1 && tabuleiro.getPosicao(x, y).leste) {
        x ++
        println("Foi para o leste")
    }
    else if("OESTE" in palavras && x > 0 && tabuleiro.getPosicao(x, y).oeste) {
        x --
        println("Foi para o oeste")
        if(x == 0 && y == 2 && vela !in carga){
            println("Você entrou no quarto escuro sem a vela. Sua perna ficou enrostada em uma estátua e você caiu no chão, quebrando a perna e morrendo por desidratação")
            state = false
            return
        }
        else if(x == 0 && y == 2){
            println("Sua Vela ilumina o quarto escuro, revelando estátuas com armaduras medievais, todas empueradas e tomadas por aranhas")
        }
    }
    else {
        println("Invalid Direction")
    }
    println("x = $x, y = $y")
    println(tabuleiro.getPosicao(x, y).descricao)
}
//function to grab things on the desired board space
fun pegar(palavras: List<String>){
    if("CHAVE" in palavras && chave in tabuleiro.getPosicao(x, y).objectList){
        if(carga.size <= 3) {
            carga.add(chave)
            retirarObjTab(chave)
            println("Você pegou a Chave")
        }
        else{
            println("Você está carregando muita coisa! solte algum item para poder carregar este")
        }
    }
    else if("VELA" in palavras && vela in tabuleiro.getPosicao(x, y).objectList){
        if(carga.size <= 3) {
            carga.add(vela)
            retirarObjTab(vela)
            println("Você pegou a Vela")
        }
        else{
            println("Você está carregando muita coisa! solte algum item para poder carregar este")
        }
    }
    else if("CAFÉ" in palavras && cafe in tabuleiro.getPosicao(x, y).objectList){
        if(carga.size <= 3) {
            carga.add(cafe)
            retirarObjTab(cafe)
            println("Você pegou o Café")
        }
        else{
            println("Você está carregando muita coisa! solte algum item para poder carregar este")
        }
    }
    else if("ESPADA" in palavras && espada in tabuleiro.getPosicao(x, y).objectList){
        if(carga.size <= 3) {
            carga.add(espada)
            retirarObjTab(espada)
            println("Você pegou a Espada")
        }
        else{
            println("Você está carregando muita coisa! solte algum item para poder carregar este")
        }
    }
    else if("COROA" in palavras && coroa in tabuleiro.getPosicao(x, y).objectList){
        if(carga.size <= 3) {
            carga.add(coroa)
            retirarObjTab(coroa)
            println("Você pegou a Coroa")
            fim(true)
        }
        else{
            println("Você está carregando muita coisa! solte algum item para poder carregar este")
        }
    }
    else if("BAÚ" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Bau){
        println("O baú é muito pesado para você carregar ele")
    }
    else if("AÇÚCAR" in palavras && acucar in tabuleiro.getPosicao(x, y).objectList){
        if(carga.size <= 3) {
            carga.add(acucar)
            retirarObjTab(acucar)

            println("Você pegou o Açúcar")
        }
        else{
            println("Você está carregando muita coisa! solte algum item para poder carregar este")
        }
    }
    else{
        println(tabuleiro.getPosicao(x, y).objectList.toString())
        println("Esse item não está aqui")
    }
}
//function to release things on the desired board space
fun soltar(palavras: List<String>){
    if("CHAVE" in palavras && chave in carga){
        carga.remove(chave)
        tabuleiro.getPosicao(x, y).addObjeto(chave)
        println("A Chave foi colocada no chão")
    }
    if("VELA" in palavras && vela in carga){
        carga.remove(vela)
        tabuleiro.getPosicao(x, y).addObjeto(vela)
        println("A Vela foi colocada no chão")
    }
    else if("CAFÉ" in palavras && cafe in carga){
        carga.remove(cafe)
        tabuleiro.getPosicao(x, y).addObjeto(cafe)
        println("O Café foi colocada no chão")
    }
    else if("ESPADA" in palavras && espada in carga){
        carga.remove(espada)
        tabuleiro.getPosicao(x, y).addObjeto(espada)
        println("A Espada foi colocada no chão")
    }
    else if("COROA" in palavras && coroa in carga) {
        carga.remove(coroa)
        tabuleiro.getPosicao(x, y).addObjeto(coroa)
        println("A Coroa foi colocada no chão")
    }
    else if("AÇÚCAR" in palavras && acucar in carga){
        carga.remove(acucar)
        tabuleiro.getPosicao(x, y).addObjeto(acucar)
        println("O Açúcar foi colocada no chão")
    }
    else{
        println("Você não está carregando este item!!!")
    }
}
//SEE function. It allows the player to see the object in certain spot
fun ver(palavras:List<String>){
    if("CHAVE" in palavras && chave in tabuleiro.getPosicao(x, y).objectList){
        println("Essa chave abre o baú, o que será que tem lá dentro???")
    }
    else if("VELA" in palavras && vela in tabuleiro.getPosicao(x, y).objectList){
        println("Essa vela pode iluminar um certo corredor escuro")
    }
    else if("CAFÉ" in palavras && cafe in tabuleiro.getPosicao(x, y).objectList){
        println("Um café feito agora pouco, porém será que ele está doce o suficiente para os gostos do Goblin")
    }
    else if("ESPADA" in palavras && espada in tabuleiro.getPosicao(x, y).objectList){
        println("Uma espada que pode ser usada contra Goblins Raivosos, mas será que temos de usar a violência mesmo? Aquele café ali na frente pode ajudar")
    }
    else if("COROA" in palavras && coroa in tabuleiro.getPosicao(x, y).objectList){
        println("Ali está a Coroa, a aquela que estamos procurando faz tanto tempo")
    }
    else if("BAÚ" in palavras && bau in tabuleiro.getPosicao(x, y).objectList){
        println("Este baú contém a açúcar e também tem alguma coisa se mexendo lá dentro, sons de garrafas de vinho batendo")
    }
    else if("AÇÚCAR" in palavras && acucar in tabuleiro.getPosicao(x, y).objectList){
        println("5 cubos de açõcar, esse Goblin vai ficar diabético desse jeito")
    }
    else{
        println("Este item não aqui!!!")
    }
}
//function to open the chest in room 3, 3
fun abrir(){
    if(x == 3 && y == 3){
        if(chave in carga){
            println("Você abriu o baú e dentro dele sai um tal de Senhor Mini falando sobre vinhos. Há também um pouco de açúcar, tomara que ele não tenha usado para adoçar os vinhos...")
            tabuleiro.getPosicao(3, 3).addObjeto(acucar)
        }
        else{
            println("Você não possui a chave para abrir este baú")
        }
    }
    else{
        println("Não existe nada para abrir aqui")
    }
}
//function to attack goblin
fun atacar(){
    if(x == 3 && y==1){
        if(espada in carga && cafe !in carga){
            println("Goblins são ótimos com espadas! Depois de uma longa batalha você consegue voltar ao corredor interno sem que o Goblin corra atrás de você")
            ir(listOf("SUL"))
        }
        else if(cafe in carga && acucar !in carga){
            println("Você deu café para o Goblin, mas estava amargo! Ele ficou muito bravo e foi para cima de você")
            fim(false)
        }
        else if(cafe in carga && acucar in carga && espada in carga){
            println("Você dá o café para o Goblin, fazendo com que ele se distraia com a deliciosa bebida branca. Você aproveita esse momento para o atacar.")
            println("A Coroa está ao norte, basta somente ir até lá e tomar ela de seu amaldiçoado lugar")
            goblin = false
        }
        else{
            println("Você percebe que não tem uma espada para lutar com o Goblin, sua visão fica turva segundo depois")
            fim(false)
        }
    }
    else{
        println("Não há nada para atacar aqui!")
    }
}
//Displays what player is carrying
fun carga(){
    println(carga.toString())
    println("Você está com espaço para " + (3 - carga.size) + " Itens")
}
//Turns Player input into list of upper case words
fun getPalavras(comando:String): List<String> {
    return comando.toUpperCase().split(" ")
}
//Interprets different commands player can use
fun interpretar(comando: List<String>){
    if("IR" in comando) {
        ir(comando)
    }
    else if("VER" in comando){
        ver(comando)
    }
    else if("PEGAR" in comando){
        pegar(comando)
    }
    else if("CARGA" in comando){
        carga()
    }
    else if("SOLTAR" in comando){
        soltar(comando)
    }
    else if("ABRIR" in comando){
        abrir()
    }
    else if("ATACAR" in comando){
        atacar()
    }
    else{
        println("Comando Inválido")
    }
}
//main funtion - core logic in here
fun main (args: Array<String>){
    fillPosicao()
    println(KotlinMessages.WELCOME)
    println(KotlinMessages.OBJETIVO)
    println("Insert a comand mortal")
    while(state){
        print(">>")
        var comando = readLine()
        println("Comando: $comando")
        if(comando!!.toUpperCase() == "SAIR"){
            println(KotlinMessages.SAIR)
            state = false
        }
        else {
            interpretar(getPalavras(comando))
        }
    }
}