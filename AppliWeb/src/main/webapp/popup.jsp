<%-- 
    Document   : popup
    Created on : 12 mai 2017, 09:13:38
    Author     : Anthony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CollectIF</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <script src="https://www.w3schools.com/lib/w3data.js"></script>

        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.js"></script>
    </head>
    <body>
        <div class="row">
            <h2>Détails sur l'évènement</h2>
        </div>
        
        <div class="row">
            <h3 id="activite">Activite</h3>
        </div>
        
        <div class="row">
            <div class="col s6">
                <p>Date :</p>
            </div>
            <div class="col s6">
                <p id="date">date</p>
            </div>
        </div>
        
        <div class="row">
            <div class="col s6">
                <p>Lieu :</p>
            </div>
            <div class="col s6">
                <p id="lieu">lieu</p>
            </div>
        </div>
        
        <div class="row">
            <div class="col s6">
                <p>Participation :</p>
            </div>
            <div class="col s6">
                <p id="prix">lieu</p>
            </div>
        </div>
        
        <div class="row">
            <div class="col s6">
                <p>Avec :</p>
            </div>
            <div class="col s6">
                <p id="personnes">personnes</p>
            </div>
        </div>
        
        <div class="row">
            <h4 id="valide" class="center-align">Valide</h4>
        </div>

        <script>
            $(document).ready(function () {
                var id = getUrlParameter("id");
                console.log(id);

                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getDetails",
                        id: id
                    },
                    dataType: "json"
                })
                        .done(function (data) {
                            console.log(data);
                            $("#activite").html(data.details.activite);
                            $("#date").html(data.details.date+" ("+data.details.moment+")");
                            $("#lieu").html(data.details.lieu);
                            $("#prix").html(data.details.prix+" €");
                            var personnes = data.personnes;
                            $("#personnes").html("");
                            $.each(personnes, function (i, personne) {
                                $("#personnes").append("- "+personne.nom + " "+ personne.prenom+ "<br/>");
                            })
                            if(data.details.lieu != "-"){
                                $("#valide").html("Validé");
                                $("#valide").addClass("green-text");
                            }
                            else{
                                $("#valide").html("Non validé");
                            }
                        })
                        .fail(function () {
                            $("#nom").html("ERREUR");
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            });
        </script>

        <script>
            var getUrlParameter = function getUrlParameter(sParam) {
                var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                        sURLVariables = sPageURL.split('&'),
                        sParameterName,
                        i;

                for (i = 0; i < sURLVariables.length; i++) {
                    sParameterName = sURLVariables[i].split('=');

                    if (sParameterName[0] === sParam) {
                        return sParameterName[1] === undefined ? true : sParameterName[1];
                    }
                }
            };
        </script>
    </body>
</html>
