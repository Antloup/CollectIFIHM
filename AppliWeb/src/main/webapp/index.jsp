<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<t:basic_layout title="Inscription" nav_inscription="active" nav_connected="hide">
 
<jsp:attribute name="head_area">
</jsp:attribute>
<jsp:attribute name="body_area">
  <div class="row">
            <div class="col s6 center-align">
                <h2>Connexion</h2>
                <a class="waves-effect waves-light btn-large" href='connexion.jsp'>Connexion</a>
            </div>
            <div class="col s6 center-align">
                <h2>Inscription</h2>
                    <div class="row">
                        <div class="input-field s6 center-align">
                            <input id="nom" type="text" class="validate">
                            <label for="nom">Nom</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field s6">
                            <input id="prenom" type="text" class="validate">
                            <label for="prenom">Prenom</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field s6">
                            <input id="email" type="email" class="validate">
                            <label for="email">Email</label>
                        </div>
                    </div>
                
                    <div class="row">
                        <div class="input-field s6">
                            <textarea id="adresse" class="materialize-textarea"></textarea>
                            <label for="adresse">Adresse</label>
                        </div>
                    </div>
                    <div class="row">
                        <button class="btn waves-effect waves-light" id="inscription">Valider l'inscription</button>
                    </div>

            </div>
        </div>
    <script>
            $("#inscription").click(function() {
                console.log( "Requete ajax inscription." );
                console.log( $("#nom")[0].value);
                console.log( $("#prenom")[0].value);
                console.log( $("#email")[0].value);
                console.log( $("#adresse")[0].value);
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action : "Inscription",
                        //id: window.location.search.substring(1), // URL.html?<id>
                        nom: $("#nom")[0].value,
                        prenom: $("#prenom")[0].value,
                        email: $("#email")[0].value,
                        adresse: $("#adresse")[0].value
                    },
                    dataType: "json"
                })
                .done(function(data) {
                    console.log(data);
                    if(data.Inscription == "OK"){
                        console.log("Inscription OK")
                        window.location.href = "accueil.jsp";
                    }
                    else{
                        console.log("Erreur Inscription")
                        Materialize.toast('Erreur lors de l\' inscription', 4000);
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