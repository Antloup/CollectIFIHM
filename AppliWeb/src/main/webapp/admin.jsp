<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic_layout title="Responsable" nav_connected="hide">

    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="row center-align">
            <h2>Evenements à valider :</h2>
        </div>
        <div class="row">

            <table class="striped">
                <thead>
                    <tr>
                        <th>Denomination</th>
                        <th>Date</th>
                        <th>Moment de la journée</th>
                        <th>Gratuit/Payant</th>
                        <th>Valider</th>
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
                        action: "getListeDemandes",
                        id: window.location.search.substring(1) // URL.html?<id>
                    },
                    dataType: "json"
                })
                        .done(function (data) {
                            var recherche = getUrlParameter('search');
                            if(recherche != null){
                                $("#search").val(recherche);
                            }
                            console.log(data);
                            var activites = data.activites;
                            console.log(activites);
                            var listeEvenements = $("#dataEvenements");
                            listeEvenements.empty();
                            $.each(activites, function (i, activite) {
                                if(recherche == null || activite.denomination.search( recherche )>=0){
                                    // Si pas de recherche ou que denomination correspond a recherche
                                listeEvenements.append("<tr>");
                                listeEvenements.append("<td>" + activite.denomination + "</td>");
                                listeEvenements.append("<td>" + activite.date + "</td>");
                                listeEvenements.append("<td>" + activite.moment + "</td>");
                                listeEvenements.append("<td>" + activite.denomination + "</td>");
                                listeEvenements.append("<td>" + activite.nb_participants + "</td>");
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
    </jsp:attribute>

</t:basic_layout>