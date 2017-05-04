<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic_layout title="Connexion" nav_connexion="active" nav_connected="hide">

    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="row">
            <div class="col s6 valign-wrapper">
                <a class="waves-effect waves-light col btn" href='index.jsp'>Retour a l'inscription</a>
            </div>

            <div class="col s6 center-align">
                <h2>Connexion</h2>
                <div class="row center-align">
                    <div class="input-field">
                        <i class="material-icons prefix">email</i>
                        <input id="email" type="email">
                        <label for="email">Email</label>
                    </div>
                </div>
                
                <div class="row center-align">
                    <button class="btn waves-effect waves-light" id="connexion">Connexion</button>
                </div>
            </div>
        </div>
        
        <script>
            $( "#connexion" ).click(function() {
                console.log( "Requete ajax Connexion." );
                console.log( $("#email")[0].value);
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action : "Connexion",
                        email: $("#email")[0].value
                    },
                    dataType: "json"
                })
                .done(function(data) {
                    //console.log(data);
                    if(data.Connexion=="OK"){
                        console.log("Connexion OK")
                        window.location.href = "accueil.jsp";
                    }
                    else{
                        console.log("Erreur Connexion")
                        Materialize.toast('Adresse incorrect', 4000);
                    }
                    // Si c'est ok : Redirection Page accueil
                })
                .fail(function() {
                    console.log("fail");
                    // Gérer erreur
                })
                .always(function() {
                    console.log("always");
                });
            });
        </script>
    </jsp:attribute>

</t:basic_layout>