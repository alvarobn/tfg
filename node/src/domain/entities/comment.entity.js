class Comment{
    constructor(id, autor, contenido, fecha, entry_id, user_comment_id){
        this.id = id;
        this.autor = autor;
        this.contenido = contenido;
        this.fecha = fecha;
        this.entry_id = entry_id;
        this.user_comment_id = user_comment_id
    } 
}
module.exports = {
    Comment
}