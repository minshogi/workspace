
function getRandomInt() {
  min = Math.ceil(0);
  max = Math.floor(9);
  return Math.floor(Math.random() * (max - min)) + min; //최댓값은 제외, 최솟값은 포함
}

function go()
{
    var a, mat = [];
    for (let i = 0; i < 9; i++)
    {
        a = [];
        for (let j = 0; j < 9; j++)
        {
            a[j] = getRandomInt();
        }
        mat[i] = a;
    }
    make_matrix(mat, 9, 9);
}

function make_matrix(barray, R, C)
{
    var tag = '<table border="1">';
    for (let i = 0; i < R; i++) {
        tag+='<tr>';
        for (let j = 0; j < C; j++)  {
            var id = 'b' + i + ',' + j;
            tag += '<td id='+'"'+id + '"' + '>';
            if (barray[i][j] == 0)
            {
                tag += '<input type="number" min="1" max="9" id="'+id+'_"/>';
            }else
            {
                tag += barray[i][j];
            }
           tag+='</td>';
        }
        tag+='</tr>'
    }
    tag+='</table>';
    
    document.getElementById("board").innerHTML = tag;
    document.getElementById("buttonForBoard").style.visibility = "visible";
}

function submit()
{
    var mat = [];
    for (let i = 0; i < 9; i++){
        var a = [];
        for (let j = 0; j < 9; j++){
            a[j] = Value(i, j);
        }
        mat[i] = a;
    }
    var output = "";
    for (let i = 0; i < 9; i++) {
        for (let j = 0; j < 9; j++){
            output += mat[i][j];
        }
        output += '\n';
    }
    alert(output);
}

function Value(r, c)
{
    var id = 'b' + r + ',' + c;
    var x = document.getElementById(id).innerHTML;
    if (x.length > 1) x = document.getElementById(id + '_').value;
    return x;
}

function Print(r, c)
{
    alert(Value(r, c));
}