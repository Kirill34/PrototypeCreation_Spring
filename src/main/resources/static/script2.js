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
let componentsWithDataType = {}

let componentParameters = {}
let returnComponent = null


//document.getElementById("next-interaction").onload = () =>{this.hidden=true}


function nextInteraction()
{
    let thead = table.getElementsByTagName("thead")[0]
    let th = document.createElement("th")
    let interactionText = document.getElementById("interaction-text")
    switch (interaction)
    {
        case 1:
            thead.appendChild(th)
            th.innerText="Направление данных"
            interactionText.innerText="Каждый элемент данных имеет направление: входные данные, выходные данные или обновляемые данные. Вам необходимо для каждого элемента данных определить его направление."
            addDataElementDirections()
            break
        case 2:
            thead.appendChild(th)
            th.innerText="Представление данных"
            interactionText.innerText="Каждый элемент данных может быть представлен каким-либо образом. Вам необходимо выбрать представление для каждого элемента данных."
            addDataElementPresentations()
            break
        case 3:
            let table45 = document.createElement("table")
            let thead45 = document.createElement("thead")
            table45.appendChild(thead45)
            let th2 = document.createElement("th")
            thead45.appendChild(th2)
            th.appendChild(table45)
            thead.appendChild(th)
            interactionText.innerText="Каждый компонент данных может быть передан в функцию как входной, выходной или обновляемый параметр или быть возвращаемым значением функции. Вам необходимо для каждого компонента данных выбрать метод передачи в функцию."
            th2.innerText="Метод передачи в функцию"
            addComponents()
            break
        case 4:
            let thead_45 = thead.getElementsByTagName("th")[3].getElementsByTagName("thead")[0]
            thead_45.appendChild(th)
            interactionText.innerText="Каждый параметр и возвращаемое значение (при его наличии) имеет тип данных. Вам необходимо выбрать типы данных для всех параметров функции и возвращаемого значения."
            th.innerText="Тип данных на языке Си"
            addComponentsDataTypes()
            break
        case 5:
            interactionText.innerText="Вам необходимо составить прототип функции на языке Си, используя предоставленные Вам лексемы."
            addCodeBlock()
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
                    let thead = document.createElement("thead")
                    let tbody = document.createElement("tbody")
                    table.appendChild(thead)
                    table.appendChild(tbody)
                    document.getElementsByTagName("body").item(0).appendChild(table)
                    let th = document.createElement("th")
                    th.innerText="Элемент данных"
                    thead.appendChild(th)

                    let nextBtn = document.createElement("button")
                    nextBtn.innerText="Далее >>"
                    nextBtn.classList.add("btn","btn-primary")
                    nextBtn.onclick = () => {nextInteraction()}
                    nextBtn.id = "next-interaction"
                    nextBtn.hidden=true
                    nextBtn.style.margin="20px"
                    document.getElementsByTagName("body").item(0).appendChild(nextBtn)
                }
                dataElements.push(new Object({name:obj.name, mission:obj.mission}))
                let tr = document.createElement("tr")
                let td = document.createElement("td")
                td.innerText = obj.mission
                tr.appendChild(td)
                table.getElementsByTagName("tbody").item(0).appendChild(tr)
                dataElementTableRows[obj.name]=tr
                //alert("Добавлен новый элемент")
            }
            else
            {
                //alert(obj.message)
                document.getElementById("element-selection-error").innerText=obj.message
                document.getElementById("element-selection-error").hidden=false
                setTimeout(()=>{document.getElementById("element-selection-error").hidden=true;}, 3000)
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
            let block = createSelectBlock(options, "/answer/5", {'student':studentID,'parameterName':componentParameters[dataElementName][componentName]}, "datatype",5)
            let td = document.createElement("td")
            td.appendChild(block)
            tr.appendChild(td)
        }
    }
    if (returnComponent != null)
    {
        let tr = componentsTableRows[returnComponent.element][returnComponent.component]
        let options = {"Type_Int": "int", "Type_PointerToInt" : "int *", "Type_Float" : "float", "Type_Char": "char"}
        let block = createSelectBlock(options, "/answer/5", {'student':studentID,'parameterName':'return'}, "datatype",5)
        let td = document.createElement("td")
        td.appendChild(block)
        tr.appendChild(td)
    }
}

function addCodeBlock()
{
    let sequenceParam = document.createElement("p")
    sequenceParam.innerText="Порядок параметров: "

    let xhrp = new XMLHttpRequest()
    xhrp.open("GET","/answer/parametersWithDescription?student="+studentID)
    xhrp.responseType="json"
    xhrp.send()
    xhrp.onload = () => {
        let obj = xhrp.response
        console.log("Порядок параметров")
        console.log(obj)
        for (let key in obj)
        {
            sequenceParam.innerText+=obj[key].name+","
        }
    }


    let block = document.createElement("div")
    block.classList.add("card")
    block.style.margin="20px"
    block.style.padding="20px"
    document.getElementsByTagName("body").item(0).appendChild(block)
    let title = document.createElement("p")
    title.classList.add("card-title")
    title.innerText = "Составьте прототип функции"
    block.appendChild(title)
    block.appendChild(sequenceParam)
    let cardBody = document.createElement("div")
    cardBody.classList.add("card-body")
    block.appendChild(cardBody)

    let codeAlert = document.createElement("div")
    codeAlert.classList.add("alert","alert-primary")

    let errorAlert = document.createElement("div")
    errorAlert.classList.add("alert", "alert-danger")
    errorAlert.hidden=true


    cardBody.appendChild(codeAlert)
    cardBody.appendChild(errorAlert)

    let btnBlock = document.createElement("div")
    cardBody.appendChild(btnBlock)

    let xhr = new XMLHttpRequest()
    xhr.open("GET","/answer/lexemsForPrototype?student="+studentID)
    xhr.responseType="json"
    xhr.send()
    xhr.onload = ()=>{
        let arr = xhr.response
        for (let key in arr)
        {
            let obj = arr[key]
            let type = obj["type"]
            let value=obj.value
            let btn = document.createElement("button")
            btn.classList.add("btn","btn-primary")
            btn.innerText = value
            btn.style.margin="20px"
            btnBlock.appendChild(btn)
            btn.onclick = ()=>{
                let xhr2 = new XMLHttpRequest()
                xhr2.open("POST", "/answer/6/addLexem?student="+studentID+"&lexemType="+type+"&lexemValue="+value)
                xhr2.responseType="json"
                xhr2.send()

                xhr2.onload = ()=>{
                    let respObj = xhr2.response
                    if (respObj.correct === "true")
                    {
                        errorAlert.hidden=true
                        btnBlock.childNodes.forEach((child)=>{child.disabled=false})
                    }
                    else
                    {
                        btnBlock.childNodes.forEach((child)=>{child.disabled=true})
                        errorAlert.innerText=respObj.message
                        errorAlert.hidden=false
                    }
                    codeAlert.innerText = respObj.code
                }

            }
        }

        let removeLastLexem = document.createElement("div")
        removeLastLexem.classList.add("btn","btn-danger")
        removeLastLexem.innerText="Удалить последнюю лексему"
        removeLastLexem.onclick = () => {
            let xhr2 = new XMLHttpRequest()
            xhr2.open("POST","/answer/6/removeLexem?student="+studentID)
            xhr2.responseType="json"
            xhr2.send()
            xhr2.onload = () => {
                let respObj = xhr2.response
                codeAlert.innerText = respObj.code
                btnBlock.childNodes.forEach((child)=>{child.disabled=false})
                errorAlert.hidden=true
            }
        }
        cardBody.appendChild(removeLastLexem)
    }

}

function createSelectBlock(options, url, paramDict, thisParamName, interactionNum)
{
    let block = document.createElement("div")
    let select = createSelect(options)
    let div = document.createElement("div")
    div.classList.add("alert")
    div.style.fontSize = "12px"
    div.style.width = "320px"

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
        case 5:
            //alert("param name: "+specificDict.parameterName)
            if (specificDict.parameterName === "return")
            {
                if (!componentsWithDataType.hasOwnProperty(returnComponent.element))
                    componentsWithDataType[returnComponent.element] = []
                componentsWithDataType[returnComponent.element].push(returnComponent.component)
            }
            else
            {
                for (let elName in componentParameters)
                {
                    for (let compName in componentParameters[elName])
                    {
                        if (componentParameters[elName][compName] === specificDict.parameterName)
                        {
                            if (!componentsWithDataType.hasOwnProperty(elName))
                                componentsWithDataType[elName] = []
                            componentsWithDataType[elName].push(compName)
                        }
                    }
                }
            }
            if (allComponentsHaveDataTypes())
            {
                alert("Вы корректно выбрали все типы данных. Для перехода к следующему позаданию нажмите \"Далее\"")
                let nextInteractionButton = document.getElementById("next-interaction")
                nextInteractionButton.hidden=false
            }
            break
    }
}

function allComponentsHaveDataTypes()
{
    for (let elementName in components)
    {
        if (!componentsWithDataType.hasOwnProperty(elementName))
            return false
        if (componentsWithDataType[elementName].length < components[elementName].length)
            return false
    }
    return true
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