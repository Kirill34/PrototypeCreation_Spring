let studentID = 1
let interaction = 1
let dataElements=[]
let dataElementPresentations = {}
let table = null
let dataElementTableRows = {}


function nextInteraction()
{
    switch (interaction)
    {
        case 1:
            addDataElementDirections()
            break
    }
    interaction++
    let nextInteractionButton = document.getElementById("next-interaction")
    nextInteractionButton.hidden=true
}

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
            if (obj.hasOwnProperty("interaction"))
            {
                if (obj.interaction === "1")
                {
                    let nextInteractionButton = document.getElementById("next-interaction")
                    nextInteractionButton.hidden=false
                }
            }
        }

    }

}

function addDataElementDirections()
{
    for (let el in dataElementTableRows)
    {
        let td = document.createElement("td")
        td.appendChild(createSelectBlock({"input":"Входные данные","output":"Выходные данные","updatable":"Обновляемые данные"}, "answer/2", {"student":studentID,"elementName":el}, "elementDirection"))
        //td.appendChild(createSelect({"input":"Входные данные","output":"Выходные данные","updatable":"Обновляемые данные"}))
        dataElementTableRows[el].appendChild(td)
    }
}

function addDataElementPresentations()
{
    for (let key in dataElements)
    {
        let currElement = dataElements[key]["name"]
        let xhr = new XMLHttpRequest()
        xhr.open("GET","/answer/presentations?student="+studentID+"&elementName="+currElement)
        xhr.responseType="json"
        xhr.send()
        xhr.onload = () => {
            let obj = xhr.response
            dataElementPresentations[currElement] = obj

            let td = document.createElement("td")
            td.appendChild(createSelect(obj))
            dataElementTableRows[currElement].appendChild(td)
        }
    }
}


function createSelectBlock(options, url, paramDict, thisParamName)
{
    let block = document.createElement("div")
    let select = createSelect(options)
    let div = document.createElement("div")
    div.classList.add("alert")

    block.appendChild(select)
    block.appendChild(div)

    let paramString = ""
    for (let key in paramDict)
    {
        paramString += key+"="+paramDict[key]+"&"
    }
    paramString += thisParamName+"="

    select.onchange = (ev) => {
        //console.log(ev.srcElement.value);
        //console.log(url)

        let xhr = new XMLHttpRequest()
        xhr.open("POST", url+"?"+paramString+ev.srcElement.value)
        xhr.responseType="json"
        xhr.send()
        console.log("Response")
        xhr.onload = () => {
            let obj = (xhr.response)
            if (obj.correct === "true")
            {
                select.disabled=true
                div.hidden=true
            }
            else
            {
                div.classList.add("alert-danger")
                div.innerText = obj.message
            }
        }


    }
    return block
}

function createSelect(options)
{
    let select = document.createElement("select")
    let optionSelect = document.createElement("option")
    optionSelect.text = "..."
    optionSelect.disabled = true
    optionSelect.selected = true
    select.appendChild(optionSelect)
    for (let val in options)
    {
        let option = document.createElement("option")
        option.value = val
        option.text = options[val]
        select.appendChild(option)
    }

    return select
}