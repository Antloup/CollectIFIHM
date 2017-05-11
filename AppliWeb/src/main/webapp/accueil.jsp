<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic_layout title="Accueil" nav_preparation="active" nav_disconnected="hide">

    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="row center-align">
            <h2>Bienvenue sur Collect'IF</h2>
        </div>
        <div class="row">
            <h5>Demandes en cours de création :</h5>
        </div>
        <div class="row">
            <div class="col s6 valign-wrapper">
                <nav>
                    <div class="nav-wrapper">
                        <form>
                            <div class="input-field">
                                <input id="search" type="search" required class="autocomplete">
                                <label class="label-icon" for="search"><i class="material-icons" onclick="search();">search</i></label>
                                <i class="material-icons" >close</i>
                            </div>
                        </form>
                    </div>
                </nav>

            </div>

            <div class="col s6 valign-wrapper">
                <a class="btn waves-effect waves-light" id="demande" href="demande.jsp">Créer une demande</a>
            </div>
        </div>
        <div class="row">

            <table class="striped">
                <thead>
                    <tr>
                        <th>Denomination</th>
                        <th>Date</th>
                        <th>Moment de la journée</th>
                        <th>Gratuit/Payant</th>
                        <th>Participants</th>
                    </tr>
                </thead>

                <tbody id="dataEvenements">
                    <tr>
                        <td>Chargement...</td>
                        <td>Chargement...</td>
                        <td>Chargement...</td>
                        <td>Chargement...</td>
                        <td>Chargement...</td>
                    </tr>
                </tbody>
            </table>

        </div>

        <script>
            $(document).ready(function () {
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getListeEvenements",
                        id: window.location.search.substring(1) // URL.html?<id>
                    },
                    dataType: "json",
                    success: function (data) {
                        getActivite();
                    }
                })
                        .done(function (data) {
                            var recherche = getUrlParameter('search');
                            if (recherche != null) {
                                $("#search").val(recherche);
                            }
                            console.log(data);
                            var activites = data.activites;
                            console.log(activites);
                            var listeEvenements = $("#dataEvenements");
                            listeEvenements.empty();
                            $.each(activites, function (i, activite) {
                                if (recherche == null || activite.denomination.search(recherche) >= 0) {
                                    // Si pas de recherche ou que denomination correspond a recherche
                                    listeEvenements.append("<tr>");
                                    listeEvenements.append("<td>" + activite.denomination + "</td>");
                                    listeEvenements.append("<td>" + activite.date + "</td>");
                                    listeEvenements.append("<td>" + activite.moment + "</td>");
                                    listeEvenements.append("<td>" + activite.denomination + "</td>");
                                    listeEvenements.append("<td>" + activite.nb_participants + "/" + activite.nb_max + "</td>");
                                    listeEvenements.append("</tr>");
                                }
                            })
                            if (activites.length == 0) {
                                listeEvenements.append("<tr> <td>Pas d'évènement en cours...</td> <td></td> <td></td> <td></td> <td></td> </tr>");
                            }
                            console.log("done");
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
            function getActivite(){
                // getActivites
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getActivites"
                    },
                    dataType: "json"
                })
                        .done(function (data) {

                            console.log(data);
                            var activites = data.activites;
                            console.log(activites);
                            var dateActivite = {};
                            $.each(activites, function (i, activite) {
                                dateActivite[activite.denomination] = null;
                            })
                            console.log("done");
                            $('input.autocomplete').autocomplete({
                                data: dateActivite,
                                limit: 5, // The max amount of results that can be shown at once. Default: Infinity.
                                onAutocomplete: function (val) {
                                    search();
                                },
                                minLength: 1, // The minimum length of the input for the autocomplete to start. Default: 1.
                            });
                            console.log($('input.autocomplete').data);
                        })
                        .fail(function () {
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            }

        </script>


    </jsp:attribute>

</t:basic_layout>