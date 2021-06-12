package ahmed.adel.sleeem.clowyy.souq.pojo

data class FilterParams (
     var min:Int?=null,
     var max:Int?=null,
     var price:Int=0,
     var category:String?=null,
     var sale:Int=0,
     var brand:String?=null,
     var title:String?=null,
     var page:Int=1
)