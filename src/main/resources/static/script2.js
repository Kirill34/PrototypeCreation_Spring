let studentID = 1
let dataElements=[]
let dataElementPresentations = {}
let table = null
let dataElementTableRows = {}

function checkDataElementBorders()
{
    let range = getRangeObject();
    let text = document.getElementById("problem-text").innerText;
    let lexemNum = 0;
    let lexemOfChar = {}
    for (let i=0; i<text.length; i++)
    {
        if (text[i]===" ")
            lexemNum++
        lexemOfChar[i]=lexemNum
        console.log(i," ",text[i]," ",lexemNum)
    }



    //console.log(lexemOfChar)

    if (range)
    {
        let startChar = range.startOffset
        let endChar = range.endOffset-1

        if (text[startChar]===" ")
            startChar++
        if (text[endChar]===" ")
            endChar--

        let startLexem = lexemOfChar[startChar]+1
        let endLexem = lexemOfChar[endChar]+1

        let xhr = new XMLHttpRequest()
        xhr.open("POST","/answer/1?student="+studentID+"&leftBorder="+startLexem+"&rightBorder="+endLexem)
        xhr.responseType="json"
        xhr.send()

        xhr.onload = ()=>{
            let obj = xhr.response
            console.log(obj)
            console.log(obj["correct"])
            if (obj["correct"] === "true")
            {
                if (dataElements.length===0)
                {
                    table = document.createElement("table")
                    document.getElementsByTagName("body").item(0).appendChild(table)
                }
                dataElements.push(new Object({name:obj.name, mission:obj.mission}))
                let tr = document.createElement("tr")
                let td = document.createElement("td")
                td.innerText = obj.mission
                tr.appendChild(td)
                table.appendChild(tr)
                dataElementTableRows[obj.name]=tr
                alert("Добавлен новый элемент")
            }
            else
            {
                alert(obj.message)
            }
        }

    }

}

function addDataElementDirections()
{
    for (let el in dataElementTableRows)
    {
        let select = document.createElement("select")
        let option = document.createElement("option")
        option.value="input"
        option.text="Входные данные"
        select.appendChild(option)
        dataElementTableRows[el].appendChild(select)
    }
}