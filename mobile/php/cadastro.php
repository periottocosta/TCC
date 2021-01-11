<?php
    include('connection.php');
    $opcao = $_GET['opcao'];
	//$nome = $_GET['nomeCliente'];

    // ------------------------------------SALVAR------------------------------------------------	
    if ($opcao == "salvar"){
	
        $nome = $_POST['nome'];
        $cpf = $_POST['cpf'];	
        $telefone = $_POST['telefone'];
        $email = $_POST['email'];
        $tipo = $_POST['tipo'];
        $descricao = $_POST['descricao'];
		
        $query = "INSERT INTO mobile(nome,cpf,telefone,email,tipoImovel,descricao) VALUES ('$nome','$cpf','$telefone','$email','$tipo','$descricao')";
        if (mysql_query($query)) {
            echo "retorno=true";
        } else {
            echo "retorno=false";
        }
    }
    // ------------------------------------PESQUISAR------------------------------------------------	
    if ($opcao == "pesq") {
        $nome1 = $_POST['nome'];
        $query = "SELECT * FROM mobile WHERE nome LIKE '%".$nome1."%' ";
        $consulta = mysql_query($query);
        $indice = 0;
        if (mysql_num_rows($consulta) > 0) {
            echo "retorno=true";
            $arr = array();
            while ($dados = mysql_fetch_array($consulta)) {
                
				$nome = $dados['m.nome'];
                $tel = $dados['m.telefone'];
                $cpf = $dados['m.cpf'];
                $email = $dados['m.email'];
                $id = $dados['m.id'];

                $arr['nome[$indice]'] = $nome;
                $arr['telefone[$indice]'] = $tel;
                $arr['cpf[$indice]'] = $cpf;
                $arr['email[$indice]'] = $email;
                $arr['id[$indice]'] = $id;
                $indice++;
            }
			echo json_encode($arr);
        }else{
            echo "retorno=false";
        }
    }




    
?>