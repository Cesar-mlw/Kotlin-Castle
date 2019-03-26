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
                                            arrayOf<Posicao>(Posicao("Você está na cozinha", _leste = true), Posicao("Você está na cozinha", _oeste = true, _norte = true), Posicao("Você está no corredor interno", _norte = true, _sul = true), Posicao("Você está na sala do trono. Tem um Goblin que gosta de café doce aqui...", _norte = true, _sul = true)),
                                            arrayOf<Posicao>(Posicao("Você está no quarto escuro", _sul = true), Posicao("Você está no corredor interno", _leste = true, _oeste = true, _sul = true), Posicao("Você está no corredor interno", _norte = true, _sul = true, _leste = true, _oeste = true), Posicao("Você está no corredor interno", _oeste = true, _sul = true)),
                                            arrayOf<Posicao>(Posicao("Você está no jardim", _norte = true), Posicao("Você está no corredor interno, tem uma vela aqui", _norte = true), Posicao("Você está na entrada principal", _norte = true), Posicao("Você está no corredor interno, tem um baú aqui", _norte = true))
    )
    fun getPosicao(x:Int, y:Int):Posicao {
        return tabuleiro[y][x]
    }
}
fun fillPosicao(){
    tabuleiro.getPosicao(0, 3).addObjeto(Chave())
    tabuleiro.getPosicao(1, 3).addObjeto(Vela())
    tabuleiro.getPosicao(3, 3).addObjeto(Bau())
    tabuleiro.getPosicao(0, 1).addObjeto(Espada())
    tabuleiro.getPosicao(0, 0).addObjeto(Cafe())
    tabuleiro.getPosicao(3, 0).addObjeto(Coroa())
    tabuleiro.getPosicao(3, 3).addObjeto(Acucar())
}
fun ir(palavras: List<String>){
    if("NORTE" in palavras && y > 0 && tabuleiro.getPosicao(x, y).norte) {
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
    }
    else{
        println("Invalid Direction")
    }
    println("x = $x, y = $y")
    println(tabuleiro.getPosicao(x, y).descricao)
}
fun ver(palavras:List<String>){
    if("CHAVE" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Chave){
        println("Essa chave abre o baú, o que será que tem lá dentro???")
    }
    else if("VELA" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Vela){
        println("Essa vela pode iluminar um certo corredor escuro")
    }
    else if("CAFE" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Cafe){
        println("Um café feito agora pouco, porém será que ele está doce o suficiente para os gostos do Goblin")
    }
    else if("ESPADA" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Espada){
        println("Uma espada que pode ser usada contra Goblins Raivosos, mas será que temos de usar a violência mesmo? Aquele café ali na frente pode ajudar")
    }
    else if("COROA" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Coroa){
        println("Ali está a Coroa, a aquela que estamos procurando faz tanto tempo")
    }
    else if("BAU" in palavras && tabuleiro.getPosicao(x, y).objectList[0] is Bau){
        println("Este baú contém a açúcar e também tem alguma coisa se mexendo lá dentro, sons de garrafas de vinho batendo")
    }
    else if("AÇUCAR" in palavras && tabuleiro.getPosicao(x, y).objectList[1] is Acucar){
        println("5 cubos de açõcar, esse Goblin vai ficar diabético desse jeito")
    }
    else{
        println(tabuleiro.getPosicao(x, y))
        println("Não há nada de interessante para ver nesse espaço")
    }
}
fun getPalavras(comando:String): List<String> {
    return comando.toUpperCase().split(" ")
}
fun interpretar(comando: List<String>){
    if("IR" in comando) {
        ir(comando)
    }
    else if("VER" in comando){
        ver(comando)
    }
    else{
        println("Comando Inválido")
    }
}
fun main (args: Array<String>){
    var state = true
    fillPosicao()
    println(KotlinMessages.WELCOME)
    println(KotlinMessages.OBJETIVO)
    println("Insert a comand mortal")
    print(">>")
    while(state){
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