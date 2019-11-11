# android-eservices-dynamicfragmentmenu

Deuxième TP, toujours les fragments, mais de manière dynamique cette fois !



### Description
Cette application présente un standard dans le monde des applis Android : le menu glissant.
Celui-ci utilise des fragments dynamiques pour afficher le contenu choisi.
La version finale propose un système de compteur de notifications, que vous pourrez réutiliser (pour Platine, par exemple)


### Aspects techniques
Les concepts manipulés dans ce TP sont :
- Les [fragments](https://developer.android.com/guide/components/fragments) (utilisés de manière dynamique, avec des transactions)
- La Toolbar
- La SelectableNavigationView


Mais aussi plus généralement :
- La sauvegarde de l'état de l'application, notamment lors des changements d'orientation.
- Les menus Android


### A découvrir
- Observez le layout de l'activité principale, quelle est la classe racine ? Cherchez l'attribut layout_gravity, à quoi sert-il ici ?
- Où sont rangés les menus Android ?


### Objectifs
Dans la version initiale du projet, nous vous mettons à disposition toutes les ressources du projet.
Le système de compteur est déjà mis en place, mais il faudra s'en servir un peu plus tard, d'abord faire fonctionner le menu.

Il vous faut tout d'abord :
- [x] Coder la méthode setupNavigationElements de l'activité principale. Attention au système de cache :)
- [x] Remplir la méthode pour afficher le fragment dans le container. 
- [x] Maintenant, ça doit marcher. Allez sur la page Favoris, modifiez le compteur et changez le téléphone d'orientation. Vous avez compris la prochaine étape.
- [ ] Pas si simple : l'appli est composée d'une activité qui contient un fragment. Que se passe t-il quand on modifie l'orientation du téléphone ? Quelle méthodes sont ré-appelées ?
- [ ] Cherchez maintenant comment restaurer l'état d'un fragment et mettez le système en place dans le Fragment favoris.
- [ ] Il y a sûrement des choses à faire dans l'activité également pour restaurer le fragment, ajoutez le code.
- [ ] Finalement, lisez les TODOs dans l'activité principale, car on peut restaurer l'état du fragment, mais le bon fragment doit être restauré au sein de l'activité avant ça !

