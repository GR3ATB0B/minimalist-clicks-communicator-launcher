# Project Peel — Brief

The philosophy and scope for Project Peel. The launcher in this repo is the first piece; the mesh pager bridge is the next. Technical design lives in [`PEEL-mesh-pager-design.md`](./PEEL-mesh-pager-design.md).

---

## The problem (read this first)

Phones aren't the enemy. They pull double duty as both a utility and an
entertainment device, and that second half is what erodes time and
attention without anyone choosing it. The deeper issue is that smartphones
collapsed *reachability* into a single, unbounded default: be available
instantly, to everyone, always. That's not a law of nature — it's a design
choice that happened to us sometime in the last fifteen years.

Project Peel is an attempt to put a shape back around reachability, the
way "be home when the streetlights come on" used to. Not by rejecting
technology (that's not actually available — dorm key is in Apple Wallet,
classes run through Canvas, there's no getting fully out), but by building
a *specific, narrow, well-defined channel* for the handful of people who
should always be able to reach you, with real friction and real limits
built in on purpose.

The two extremes — keep the smartphone and change nothing, or go full
dumbphone — both fail. Dumbphones try to be phones, just worse ones, and
still carry the same "always reachable" assumption. What's actually needed
is a different reachability *contract*: a small allowlist, a receive
channel that isn't instant, and a reply path with enough friction that
"reachable" doesn't mean "hijackable."

The best version of this that ever existed, historically, was **pagers and
payphones** — asynchronous, bounded, no infinite scroll, no algorithm.
Project Peel is trying to rebuild that shape with modern parts, since
actual payphones and pager networks no longer exist.

## The system being built

Nash's mom is in Atlanta; Nash is at Auburn, ~90 miles away. She should be
able to reach him without cellular service, without carrying a new device,
and without changing how she already texts him (same number, same app on
her end). Nash carries a small pager. Everyone else in his life is subject
to the same allowlist/filtering logic, so the device generalizes beyond
just "mom" to "the short list of people who get through."

Full technical design — architecture, both message-flow diagrams (mermaid),
AI filtering/summarization behavior, the message-code scheme, the
dashboard/database schema, hardware list, and open questions — is in
[`PEEL-mesh-pager-design.md`](./PEEL-mesh-pager-design.md), exported
straight from the design doc. Read that file in full before writing any
code; it has the details this brief only summarizes.

**One-paragraph summary of the design:** An old Android phone (a Pixel,
likely a 3a) on Nash's dev SIM (H2O Wireless, unlimited calls/text, no
data) sits on WiFi next to a home Heltec V3 running MeshCore companion
firmware, connected over USB. Incoming SMS/calls hit a debounce window,
get sent to a small scoped cloud AI call that returns an allow/block
decision plus a summary, and — if allowed — get formatted with a unique
4-character message code and pushed out over the mesh to Nash's pocket
pager (a second Heltec V3 + M5Stack keyboard). Replies flow the same path
in reverse, using the code to route back to the right phone number. No
delivery-confirmation/retry logic by design — this is deliberately
best-effort, with a SQL-backed web dashboard as the full log and fallback
record for anything the pager misses.

## What to do in this repo

1. **Rename/reframe as Project Peel.** This repo (`peel-launcher`) already
   carries the Peel name and philosophy (minimalist Android launcher,
   reduce a phone to a tool). Treat this mesh pager bridge system as the
   next major piece of the same project, not a separate thing. Update the
   README to lead with the philosophy above, briefly, then link out to
   the full design doc.
2. **Add `PEEL-mesh-pager-design.md`** (included alongside this brief) to
   the repo, e.g. under `docs/`, so the architecture and both flowcharts
   render on GitHub.
3. **Scaffold the Android bridge app** (if not already started): SMS/call
   receiver, debounce/batch queue, cloud AI call for filter+summarize,
   MeshCore companion-radio USB link (frame protocol per MeshCore's
   `examples/companion_radio` — Companion, USB variant), message-code
   generator (counter → reversible scramble, not random), and the
   reply-routing table (code → phone number, permanent, never expires).
4. **Scaffold the dashboard**: SQL schema for the full message/call log,
   spam/archive folder, allowlist + blocklist management, code-to-number
   table. Nash-only auth, nothing fancy.
5. **Keep the tone of the whole project consistent with the philosophy**:
   defaults should favor friction and boundedness over convenience where
   the two conflict. When in doubt, ask whether a feature makes Nash *more*
   reachable by default or *less* — the latter is usually right here.

## Open questions to flag back to Nash, not silently resolve

- No MeshCore/Meshtastic mesh coverage currently exists between Atlanta
  and Auburn (~90 mi) — does v1 stay Auburn-local, or does closing that
  gap (repeaters along the corridor) become part of scope?
- Exact scramble/permutation function for the message codes isn't pinned
  down yet.
- Whether the pager should show any hint that a spam-filtered message
  existed, or stay completely silent (dashboard-only visibility).
