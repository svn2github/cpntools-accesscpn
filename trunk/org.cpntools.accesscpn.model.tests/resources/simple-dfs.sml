exception Violating of CPNToolsModel.state

fun combinator (h2, h1) = Word.<<(h1, 0w2) + h1 + h2 + 0w17
val hash = CPNToolsHashFunction combinator

fun dfs predicate states =
let
	fun equals (a, b) = a = b
	val storage =
		HashTable.mkTable (hash, equals) (1000, LibBase.NotFound)

	fun dfs'' state [] = ()
		| dfs'' state (event::events) =
			let
				val successors = CPNToolsModel.nextStates (state, event)
				val _ = dfs' successors
			in
				dfs'' state events
			end
			
	and dfs' [] = ()
		| dfs' ((state, events)::rest) =
			if Option.isSome (HashTable.find storage state)
			then dfs' rest
			else
				let
					val _ = HashTable.insert storage (state, ())
					val violates = predicate (state, events)
				in
					if predicate (state, events)
					then raise Violating state
					else (dfs'' state events; dfs' rest)
				end
in
	(dfs' states; (NONE, storage))
	handle Violating state => (SOME state, storage)
end

val none = fn _ => false
fun dead (_, events) = List.null events

val (violating, _) = dfs dead (CPNToolsModel.getInitialStates())
(*
local
open Mark Bind
in
fun initial n =
	[({Top={A=[],B1=[],B2=[],C1=[],C2=[],D=[],Limit=[(),(),(),()],Network=(),
	Received_1=[""],Received_2=[""],Receiver_1={NextRec=[1]},
	Receiver_2={NextRec=[1]},Send=packets n,
	Sender={NextSend=[1]}}},[Sender'Send_Packet
	(1,{n=1,p="1"})])]
end

fun time f x =
let
	val ss = Time.now()
	val result = f x
	val ee = Time.now()
	val spent = Time.- (ee, ss)
in
	(Time.toString spent, result)
end

fun test n =
let
	val (time, (state, storage)) = 
		time (dfs none) (initial n)
in
	(time, state, HashTable.numItems storage)
end
*)
