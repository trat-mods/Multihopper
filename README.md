# Multihopper

![recipe](https://user-images.githubusercontent.com/31132987/80294001-3e9acb00-8765-11ea-943f-e726f7833d1c.png)

## Functionality

> [!NOTE]
> The Multihopper is used to transfer multiple item types with a single hopper by preventing the hopper to get clogged
> with items of the same type.

Multihoppers have **4** dedicated slots and a transfer cooldown of **5** ticks.   
Items are transferred using a Round Robin schedule: in cycle, every non-empty slot will transfer the
item one at a time.
Just like regular hoppers, Multihoppers can be controlled with a redstone signal.

### A practical example

We have a normal hopper that collects items from a water stream and transfers them to a target chest below. We want to
be able to
transfer different item types inside the target chest.

If we put, in order, **6** stacks of `wool` and then **2** stacks of `cobblestone` in the water stream, the classical
hopper will get clogged with **5** stacks of `wool`.
The other stacks of `cobblestone` may get lost in the process. In vanilla, this problem can be fixed using multiple
hoppers one after the other.

However, with a Multihopper, we can fix this with a single block.


> [!Tip]
> Multihoppers can be thought as **4** different smaller hoppers merged into one.
> Each slot in the Multihopper is dedicated to a single item type.

Once an item enters the Multihopper, it fills the first available slot. From that point on, that slot will be dedicated
only to that type of item, preventing items of the same type to fill other slots of the Multihopper.

> [!WARNING]
> If the streams transfer big quantities of items, a single Multihopper may lose some of them since it can hold a single
> stack of each item type. In this case it is possible to use more Multihoppers in serie to catch all the items.
