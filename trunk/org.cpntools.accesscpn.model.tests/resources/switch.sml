(*
* Module:       CPN SS switch
*
* Description:  This code captures net information, generates
*               Mark, Bind, CPNToolsEvent, and CPNToolsState
*               structures, and instantiates CPNToolsModel.
*)

(* --- extract model information --- *)
structure CPN'NetCapture = CPN'NetCapture(structure CPN'InstTable = CPN'InstTable)

val _ = CPN'NetCapture.initNet ()
val _ = CPN'NetCapture.checkNames()

(* --- generate state and associated function --- *)
structure CPN'State = CPN'State(structure CPN'NetCapture = CPN'NetCapture)

val _ = CPN'Env.use_string(CPN'State.genMark(CPN'NetCapture.getNet()));
val _ = CPN'Env.use_string(CPN'State.genState(CPN'NetCapture.getNet()));

(* --- generate events and associated function --- *)
structure CPN'Event = CPN'Event(structure CPN'NetCapture = CPN'NetCapture)

val _ = CPN'Env.use_string(CPN'Event.genBind(CPN'NetCapture.getNet()));
val _ = CPN'Env.use_string(CPN'Event.genEvent(CPN'NetCapture.getNet()));

(* --- instantiate model interface for CPN tools --- *)

structure CPNToolsModel = CPNToolsModel(structure CPNToolsState = CPNToolsState
                                        structure CPNToolsEvent = CPNToolsEvent)

(* --- generate generic hash-function --- *)
structure CPN'HashFunction = CPN'HashFunction(structure CPN'NetCapture = CPN'NetCapture)

val _ = CPN'Env.use_string(CPN'HashFunction.genHashFunction(CPN'NetCapture.getNet()));

(* --- create two instantiations of the generic hash-function --- *)
structure CPNToolsHashFunction : HASH_FUNCTION =
struct
  type state = CPNToolsModel.state

  fun combinator (h2, h1) =
    Word.<<(h1, 0w2) + h1 + h2 + 0w17

  val hash = CPNToolsHashFunction combinator
end

structure CPNToolsHashFunction2 : HASH_FUNCTION =
struct
  type state = CPNToolsModel.state

  fun combinator (h2, h1) =
    Word.<<(h1, 0w5) + h1 + h2 + 0w720

  val hash = CPNToolsHashFunction combinator
end

