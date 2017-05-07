<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic_layout title="Accueil" nav_historique="active" nav_disconnected="hide">

    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="row center-align">
            <h2>Mon Historique personnel</h2>
        </div>
        <div class="row">
            <h5>Mes demandes et évènements:</h5>
        </div>
        <div class="row">
            <div class="col s6 valign-wrapper">
                <nav>
                    <div class="nav-wrapper">
                        <form>
                            <div class="input-field">
                                <input id="search" type="search" required>
                                <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                                <i class="material-icons">close</i>
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
                        <th>Evenements</th>
                    </tr>
                </thead>

                <tbody id="dataEvenements">
                    <tr>
                        <td>Chargement...</td>
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
                        action: "getHistorique",
                        id: window.location.search.substring(1) // URL.html?<id>
                    },
                    dataType: "json"
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
                                    listeEvenements.append("<tr>");
                                    listeEvenements.append("<td>" + activite.denomination + "</td>");
                                    listeEvenements.append("<td>" + activite.date + "</td>");
                                    listeEvenements.append("<td>" + activite.moment + "</td>");
                                    listeEvenements.append("<td>" + activite.denomination + "</td>");
                                    listeEvenements.append("<td>" + activite.nb_participants + "</td>");
                                    listeEvenements.append("<td>" + activite.etat + "</td>");
                                    listeEvenements.append("</tr>");
                                }
                            })
                            if (activites.length == 0) {
                                listeEvenements.append("<tr> <td>Pas d'historique...</td> <td></td> <td></td> <td></td> <td></td> <td></td> </tr>");
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
    </jsp:attribute>

</t:basic_layout>