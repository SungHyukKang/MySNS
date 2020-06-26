// ImageUploader object ------------------------------------------------------------------
var ImageUploader = {
    vwMain: "",
    imageIdx: 0,
    imageList: [],

    init: function (vid) {
        this.vwMain = vid;
        
        $(vid).append("<input id='--photo-input' type='file' style='display:none'>");
        document.getElementById("--photo-input").onchange = function() {
            if (this.files && this.files[0]) {
                ImageUploader._addImage(this.files[0]);
            }
        };
    },
    
    add: function () {
        document.getElementById("--photo-input").click();
    },
    
    load: function (imgsrc) {
        var index = this.imageIdx++;
        
        var str = "<div id='--photo-" + index + "' class='grid-33 icon-box'>";
        str += "<div id='--photo-img-" + index + "' class='flex-embed ratio-16by9' name='previmage[]'></div>";
        str += "</div>";

        $(this.vwMain).append(str);

        this._loadImage(index, imgsrc);
    },

    get: function () {
        var list = [];
        for (var i=0; i<this.imageList.length; i++) {
            if (this.imageList[i] == null) continue;
            list.push(this.imageList[i]);
        }
        return list;
    },
    
    appendParams: function (params, name) {
    	name = isValid(name) ? name : "images";
    	
        var list = this.get();
        for (var i=0; i<list.length; i++) {
            params.append(name, list[i]);
        }
        return params;
    },

    _addImage: function (file) {
        if(!ImageUtil.checkType(file.name)) {
            alert("이미지 형식의 파일이 아닙니다.");
            return;
        }
        
        var index = this.imageIdx++;
        
        var str = "<div id='--photo-" + index + "' class='grid-33 icon-box'>";
        str += "<div id='--photo-img-" + index + "' class='flex-embed'><div class='loading'></div></div>";
        str += "</div>";
        
        $(this.vwMain).append(str);

        var reader = new FileReader();
        reader.onload = function (e) {
            ImageUploader._loadImage(index, e.target.result);
            ImageUploader.imageList.push(file);
            
            // remove the loading icon from the image frame
            $("#--photo-img-" + index).html("");
        }
        reader.readAsDataURL(file);
    },

    _loadImage: function (index, imgdata) {
        // load the image data
        $("#--photo-img-" + index).css('background-image', 'url(' + imgdata + ')');
        
        // add a remove button with index
        var str = "<img class='icon' src='images/ico_delete.png' onclick='ImageUploader._removeImage(" + index + ");'>";
        $("#--photo-" + index).append(str);
    },
    
    _removeImage: function (index) {
        var elem = document.getElementById("--photo-" + index);
        if (elem == null) return;

        this.imageList[index] = null;
        
        // MUST be invoked using JQuery because remove() is not yet the Javascript standard!
        $("#--photo-" + index).remove();
    },
};

var ImageUtil = {
    base64ToArrayBuffer: function (base64) {
        var binary_string =  window.atob(base64);
        var len = binary_string.length;
        var bytes = new Uint8Array( len );
        for (var i = 0; i < len; i++)        {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes.buffer;
    },

    checkType: function (file) {
        var ext = file.split('.').pop().toLowerCase();
        return ($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) ? false : true; 
    },
};

