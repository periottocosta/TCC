//Quando o Documento HTML estiver carregado
jQuery(document).ready(function(){
   //Ao clicar em um elemento do tipo button
   jQuery("#pesqCli").click(function(){
      //Requisição Ajax
      jQuery.ajax({
         url: "pagina.php", //URL de destino
         dataType: "json", //Tipo de Retorno
         success: function(json){ 
			
			$('#resultado').html(
            'Nome: ' + json.nome +'</br>'+
            'Tel/Cel: '+json.telefone+'</br>'+
            'CPF: '+json.cpf+'</br>'+
            'Email: '+json.email); 
      });
   });
});