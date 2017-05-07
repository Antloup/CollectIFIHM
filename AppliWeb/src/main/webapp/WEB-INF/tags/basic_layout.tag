<!DOCTYPE html>
<%@tag description="Simple Template" pageEncoding="UTF-8"%>

<%@attribute name="title"%>
<%@attribute name="nav_connexion"%>
<%@attribute name="nav_inscription"%>
<%@attribute name="nav_historique"%>
<%@attribute name="nav_demander"%>
<%@attribute name="nav_preparation"%>
<%@attribute name="nav_connected"%>
<%@attribute name="nav_disconnected"%>

<%@attribute name="head_area" fragment="true" %>
<%@attribute name="body_area" fragment="true" %>

<html>
    <head>
        <title>CollectIF - ${title}</title>
        <jsp:invoke fragment="head_area"/>
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
        
        <!-- NAVBAR -->
        <div class ="navbar-fixed">
            <nav>
                <div class="nav-wrapper">
                    <a class="brand-logo center" href="#">${title}</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li id="connexion-link" class="${nav_connexion} ${nav_disconnected}"><a href="connexion.jsp">Connexion</a></li>
                        <li id="inscription-link" class="${nav_inscription} ${nav_disconnected}"><a href="index.jsp">Inscription</a></li>
                        <li id="inscription-link" class='${nav_connected}'><a href="index.jsp">Déconnexion</a></li>
                    </ul>
                    <ul id="nav-mobile" class="left hide-on-med-and-down">
                        <li id="inscription-link" class="${nav_preparation} ${nav_connected}"><a href="accueil.jsp">Evenement en préparation</a></li>
                        <li id="inscription-link" class="${nav_demander} ${nav_connected}"><a href="demande.jsp">Demander un evenement</a></li>
                        <li id="inscription-link" class="${nav_historique} ${nav_connected}"><a href="historique.jsp">Mon historique</a></li>
                    </ul>
                </div>
            </nav>

        </div>
                    
        <jsp:invoke fragment="body_area"/>
        
        <!-- FOOTER -->
        <footer class="page-footer">
            <div class="container">
                <div class="row">
                    <h5 class="white-text">Liens</h5>
                    <ul>
                        <li><a class="grey-text text-lighten-3" href="https://www.w3schools.com/">W3School</a></li>
                        <li><a class="grey-text text-lighten-3" href="http://materializecss.com/">Materialize CSS (Framework CSS)</a></li>
                    </ul>
                </div>
            </div>
            <div class="footer-copyright">
                <div class="container">
                    2017 Augustin GAILLOT - Anthony LOUP
                </div>
            </div>
        </footer>
        
        <!-- Script recherche-->
        <script>
            $(document).ready(function () {
                $(document).keypress(function (e) {
                    if (e.which == 13) {
                        e.preventDefault();
                        search();
                    }
                });
            });
        </script>
        <script>
            function search() {
                if($("#search") != null){
                    var recherche = $("#search").val();
                    window.location.href = location.protocol + '//' + location.host + location.pathname + "?search=" + recherche;
                }
                
            }
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