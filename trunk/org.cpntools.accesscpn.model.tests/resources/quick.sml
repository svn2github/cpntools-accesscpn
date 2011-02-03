structure HashStorage = HashStorage (
structure Hash = CPNToolsHashFunction
structure Model = CPNToolsModel
)

structure HashExploration = WaitingSetExploration (
structure Model = CPNToolsModel
structure Storage = HashStorage
structure WaitingSet = Queue
)

structure SimpleHashExploration = SimpleStatsExploration (
structure Exploration = NoTraceExploration (
structure Exploration = HashExploration
)
structure Storage = HashStorage
);

val (vstates, varcs, states, arcs, _) = SimpleHashExploration.explore {init_size = 1000}
(CPNToolsModel.getInitialStates());
