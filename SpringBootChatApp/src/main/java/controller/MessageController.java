package FirstLv.Chat.controller;

import org.springframework.web.bind.annotation.*;
//fetch ('/messages',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({text:'Last message'})}).then(console.log)
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("messages")
public class MessageController {
    private int counter = 3;

    private List<Map<String, String>> msgs = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return msgs;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMsg(id);
    }

    private Map<String, String> getMsg(@PathVariable String id) {
        return msgs.stream()
                .filter(msg -> msg.get("id").equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> msg) {
        msg.put("id", String.valueOf(counter++));

        msgs.add(msg);

        return msg;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> msg) {
        Map<String, String> msgFromDb = getMsg(id);

        msgFromDb.putAll(msg);
        msgFromDb.put("id", id);

        return msgFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> msg = getMsg(id);

        msgs.remove(msg);
    }
}