class Entry{
    constructor(id, autor, contenido, descripcion, fecha, titulo, image_id,  user_entry_id){
        this.id = id;
        this.autor = autor;
        this.contenido = contenido;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.titulo = titulo;
        this.image_id = image_id;
        this.user_entry_id = user_entry_id;
    } 
}
module.exports = {
    Entry
}