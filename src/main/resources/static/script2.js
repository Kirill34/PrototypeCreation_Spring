let studentID = 1
let interaction = 1
let dataElements=[]
let dataElementPresentations = {}
let table = null
let dataElementTableRows = {}

let components = {}
let componentTables = {}
let componentsTableRows = {}

let componentsWithTransferMethods = {}

let componentParameters = {}
let returnComponent = null

document.onload = ()=>{
    document.getElementById("next-interaction").hidden=true
}

function nextInteraction()
{
    switch (interaction)
    {
        case 1:
            addDataElementDirections()
            break
        case 2:
            addDataElementPresentations()
            break
        case 3:
            addComponents()
            break
        case 4:
            addComponentsDataTypes()
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
                    table.style.margin = "20px"
                    table.style.padding = "20px"
                    table.classList.add("table")
                    table.classList.add("table-striped")
                    let tbody = document.createElement("tbody")
                    table.appendChild(tbody)
                    document.getElementsByTagName("body").item(0).appendChild(table)
                }
                dataElements.push(new Object({name:obj.name, mission:obj.mission}))
                let tr = document.createElement("tr")
                let td = document.createElement("td")
                td.innerText = obj.mission
                tr.appendChild(td)
                table.getElementsByTagName("tbody").item(0).appendChild(tr)
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
        td.appendChild(createSelectBlock({"input":"Входные данные","output":"Выходные данные","updatable":"Обновляемые данные"}, "answer/2", {"student":studentID,"elementName":el}, "elementDirection",2))
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
            td.appendChild(createSelectBlock(obj,"/answer/3",{"student":studentID,"elementName":currElement}, "elementPresentation",3))
            dataElementTableRows[currElement].appendChild(td)
        }
    }
}

function addComponents()
{
    let xhr = new XMLHttpRequest()
    xhr.open("GET","/answer/components?student="+studentID)
    xhr.responseType="json"
    xhr.send()
    xhr.onload = () => {
        components = xhr.response
        for (let key in components)
        {
            let td = document.createElement("td")
            //td.innerText = components[key]
            let componentsTable = document.createElement("table")
            componentsTableRows[key] = {}
            for (let compName in components[key])
            {
                let tr = document.createElement("tr")
                let currTd = document.createElement("td")
                componentsTable.appendChild(tr)
                tr.appendChild(currTd)
                currTd.innerText = (components[key].length === 1) ? " " : components[key][compName].mission
                componentsTableRows[key][components[key][compName].name] = tr
            }
            td.appendChild(componentsTable)
            dataElementTableRows[key].appendChild(td)
            componentTables[key]=componentsTable
        }
        addComponentsTransferMethods()
    }
}

function addComponentsTransferMethods()
{
    for (let dataElementName in components)
    {
        let currComponents = components[dataElementName]
        for (let i in currComponents)
        {
            let componentName = currComponents[i].name
            let options = {"read-only":"Входной параметр","write-only":"Выходной параметр","read-write":"Обновляемый параметр","return":"Возвращаемое значение"}
            let params = {"student":studentID,"elementName":dataElementName,"componentName":componentName}
            let block = createSelectBlock(options,"/answer/4",params,"transferMethod",4)
            componentsTableRows[dataElementName][componentName].appendChild(block)
        }
    }
}

function addComponentsDataTypes()
{
    for (let dataElementName in componentParameters)
    {
        for (let componentName in componentParameters[dataElementName])
        {
            let tr = componentsTableRows[dataElementName][componentName]
            let options = {"Type_Int": "int", "Type_PointerToInt" : "int *", "Type_Float" : "float", "Type_Char": "char"}
            let block = createSelectBlock(options, "/answer/5", {'student':studentID,'parameterName':componentParameters[dataElementName][componentName]}, "datatype")
            let td = document.createElement("td")
            td.appendChild(block)
            tr.appendChild(td)
        }
    }
    if (returnComponent != null)
    {
        let tr = componentsTableRows[returnComponent.element][returnComponent.component]
        let options = {"Type_Int": "int", "Type_PointerToInt" : "int *", "Type_Float" : "float", "Type_Char": "char"}
        let block = createSelectBlock(options, "/answer/5", {'student':studentID,'parameterName':'return'}, "datatype")
        let td = document.createElement("td")
        td.appendChild(block)
        tr.appendChild(td)
    }
}

function createSelectBlock(options, url, paramDict, thisParamName, interactionNum)
{
    let block = document.createElement("div")
    let select = createSelect(options)
    let div = document.createElement("div")
    div.classList.add("alert")
    div.style.fontSize = "12px"

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
        div.classList.remove("alert-danger")
        div.classList.add("alert-warning")
        div.innerText="Подождите... Идет проверка..."
        let xhr = new XMLHttpRequest()
        xhr.open("POST", url+"?"+paramString+ev.srcElement.value)
        xhr.responseType="json"
        xhr.send()
        console.log("Response")
        xhr.onload = () => {
            div.classList.remove("alert-warning")
            div.classList.remove("alert-danger")
            let obj = (xhr.response)
            if (obj.correct === "true")
            {
                div.classList.add("alert-success")
                div.innerText="Верно"
                select.disabled=true
                setTimeout(()=>{div.hidden=true}, 500)
                operationOfInteraction(interactionNum, paramDict, ev.srcElement.value)
            }
            else
            {
                div.classList.remove("alert-warning")
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
    select.classList.add("form-select")
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


let elementsWithDirections = []
let elementsWithPresentations = []

function operationOfInteraction(interactionNum, specificDict, selectedValue)
{
    switch (interactionNum)
    {
        case 2:
            elementsWithDirections.push(specificDict.elementName)
            if (elementsWithDirections.length === dataElements.length)
            {
                alert("Вы корректно выбрали направления для всех элементов данных. Для перехода к следующему позаданию нажмите \"Далее\"")
                let nextInteractionButton = document.getElementById("next-interaction")
                nextInteractionButton.hidden=false
            }
            break
        case 3:
            elementsWithPresentations.push(specificDict.elementName)
            if (elementsWithPresentations.length === dataElements.length)
            {
                alert("Вы корректно выбрали представления для всех элементов данных. Для перехода к следующему позаданию нажмите \"Далее\"")
                let nextInteractionButton = document.getElementById("next-interaction")
                nextInteractionButton.hidden=false
            }
            break
        case 4:
            alert("Selected value:"+selectedValue)
            if (!componentsWithTransferMethods.hasOwnProperty(specificDict.elementName))
            {
                componentsWithTransferMethods[specificDict.elementName]=[]
            }
            componentsWithTransferMethods[specificDict.elementName].push(specificDict.componentName)

            if (selectedValue === "return")
                returnComponent = {"element": specificDict.elementName, "component" : specificDict.componentName}
            else
            {
                if (!componentParameters.hasOwnProperty(specificDict.elementName))
                    componentParameters[specificDict.elementName]={}
                if (components[specificDict.elementName].length>1)
                    componentParameters[specificDict.elementName][specificDict.componentName] = specificDict.elementName + "_" + specificDict.componentName
                else
                    componentParameters[specificDict.elementName][specificDict.componentName] = specificDict.elementName
            }


            if (allComponentsHaveTransferMethods())
            {
                alert("Вы корректно выбрали способ передачи в функцию для всех компонентов. Для перехода к следующему позаданию нажмите \"Далее\"")
                let nextInteractionButton = document.getElementById("next-interaction")
                nextInteractionButton.hidden=false
            }

            break
    }
}

function allComponentsHaveTransferMethods()
{
    for (let elementName in components)
    {
        if (!componentsWithTransferMethods.hasOwnProperty(elementName))
            return false
        if (componentsWithTransferMethods[elementName].length < components[elementName].length)
            return false
    }
    return true
}