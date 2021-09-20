var Slardar = function () {
    "use strict";
    var t = function (e, n) {
        return (t = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
            t.__proto__ = e
        } || function (t, e) {
            for (var n in e) Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n])
        })(e, n)
    };

    function e(e, n) {
        if ("function" != typeof n && null !== n) throw new TypeError("Class extends value " + String(n) + " is not a constructor or null");

        function r() {
            this.constructor = e
        }

        t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }

    var n = function () {
        return (n = Object.assign || function (t) {
            for (var e, n = 1, r = arguments.length; n < r; n++) for (var o in e = arguments[n]) Object.prototype.hasOwnProperty.call(e, o) && (t[o] = e[o]);
            return t
        }).apply(this, arguments)
    };

    function r(t, e) {
        var n = {};
        for (o in t) Object.prototype.hasOwnProperty.call(t, o) && e.indexOf(o) < 0 && (n[o] = t[o]);
        if (null != t && "function" == typeof Object.getOwnPropertySymbols) for (var r = 0, o = Object.getOwnPropertySymbols(t); r < o.length; r++) e.indexOf(o[r]) < 0 && Object.prototype.propertyIsEnumerable.call(t, o[r]) && (n[o[r]] = t[o[r]]);
        return n
    }

    function o(t) {
        var e = "function" == typeof Symbol && Symbol.iterator, n = e && t[e], r = 0;
        if (n) return n.call(t);
        if (t && "number" == typeof t.length) return {
            next: function () {
                return {value: (t = t && r >= t.length ? void 0 : t) && t[r++], done: !t}
            }
        };
        throw new TypeError(e ? "Object is not iterable." : "Symbol.iterator is not defined.")
    }

    function i(t, e) {
        var n = "function" == typeof Symbol && t[Symbol.iterator];
        if (!n) return t;
        var r, o, i = n.call(t), a = [];
        try {
            for (; (void 0 === e || 0 < e--) && !(r = i.next()).done;) a.push(r.value)
        } catch (t) {
            o = {error: t}
        } finally {
            try {
                r && !r.done && (n = i.return) && n.call(i)
            } finally {
                if (o) throw o.error
            }
        }
        return a
    }

    function a(t, e, n) {
        if (n || 2 === arguments.length) for (var r, o = 0, i = e.length; o < i; o++) !r && o in e || ((r = r || Array.prototype.slice.call(e, 0, o))[o] = e[o]);
        return t.concat(r || e)
    }

    var s = "undefined" != typeof globalThis ? globalThis : "undefined" != typeof window ? window : "undefined" != typeof global ? global : "undefined" != typeof self ? self : {},
        c = (Y = function (t) {
            return t && t.Math == Math && t
        })("object" == typeof globalThis && globalThis) || Y("object" == typeof window && window) || Y("object" == typeof self && self) || Y("object" == typeof s && s) || function () {
            return this
        }() || Function("return this")(), l = {}, u = !(ir = function (t) {
            try {
                return !!t()
            } catch (t) {
                return !0
            }
        })((function () {
            return 7 != Object.defineProperty({}, 1, {
                get: function () {
                    return 7
                }
            })[1]
        })), p = {}, d = {}.propertyIsEnumerable, h = Object.getOwnPropertyDescriptor, f = h && !d.call({1: 2}, 1);

    function m(t) {
        return "object" == typeof t ? null !== t : "function" == typeof t
    }

    function v(t) {
        return Object(R(t))
    }

    p.f = f ? function (t) {
        return !!(t = h(this, t)) && t.enumerable
    } : d;
    var g = function (t, e) {
        return {enumerable: !(1 & t), configurable: !(2 & t), writable: !(4 & t), value: e}
    }, y = {}.toString, b = fe = function (t) {
        return y.call(t).slice(8, -1)
    }, w = "".split, _ = (dt = ir)((function () {
        return !Object("z").propertyIsEnumerable(0)
    })) ? function (t) {
        return "String" == b(t) ? w.call(t, "") : Object(t)
    } : Object, S = _, E = bt = function (t) {
        if (null == t) throw TypeError("Can't call method on " + t);
        return t
    }, M = function (t) {
        return S(E(t))
    }, P = m, x = function (t, e) {
        if (!P(t)) return t;
        var n, r;
        if (e && "function" == typeof (n = t.toString) && !P(r = n.call(t))) return r;
        if ("function" == typeof (n = t.valueOf) && !P(r = n.call(t))) return r;
        if (!e && "function" == typeof (n = t.toString) && !P(r = n.call(t))) return r;
        throw TypeError("Can't convert object to primitive value")
    }, R = bt, C = v, j = {}.hasOwnProperty, O = Object.hasOwn || function (t, e) {
        return j.call(C(t), e)
    }, H = m, L = c.document, T = H(L) && H(L.createElement), k = !u && !ir((function () {
        return 7 != Object.defineProperty(function (t) {
            return T ? L.createElement(t) : {}
        }("div"), "a", {
            get: function () {
                return 7
            }
        }).a
    })), A = p, U = g, D = M, I = x, B = O, q = k, J = Object.getOwnPropertyDescriptor;
    l.f = u ? J : function (t, e) {
        if (t = D(t), e = I(e, !0), q) try {
            return J(t, e)
        } catch (t) {
        }
        if (B(t, e)) return U(!A.f.call(t, e), t[e])
    };
    var F = {}, N = m, z = k, W = Gt = function (t) {
        if (!N(t)) throw TypeError(String(t) + " is not an object");
        return t
    }, Q = x, V = Object.defineProperty;
    F.f = u ? V : function (t, e, n) {
        if (W(t), e = Q(e, !0), W(n), z) try {
            return V(t, e, n)
        } catch (t) {
        }
        if ("get" in n || "set" in n) throw TypeError("Accessors not supported");
        return "value" in n && (t[e] = n.value), t
    };
    var X = F, G = g, $ = u ? function (t, e, n) {
        return X.f(t, e, G(1, n))
    } : function (t, e, n) {
        return t[e] = n, t
    }, Y = {exports: {}}, K = c, Z = $, tt = (d = f = function (t, e) {
        try {
            Z(K, t, e)
        } catch (n) {
            K[t] = e
        }
        return e
    }, H = bt = c[dt = "__core-js_shared__"] || d(dt, {}), Function.toString);
    "function" != typeof H.inspectSource && (H.inspectSource = function (t) {
        return tt.call(t)
    });
    k = p = H.inspectSource, g = "function" == typeof (x = c.WeakMap) && /native code/.test(k(x));
    var et = bt;
    ((u = {exports: {}}).exports = function (t, e) {
        return et[t] || (et[t] = void 0 !== e ? e : {})
    })("versions", []).push({version: "3.14.0", mode: "global", copyright: "© 2021 Denis Pushkarev (zloirock.ru)"});
    var nt, rt, ot, it, at, st, ct, lt, ut = 0, pt = Math.random(), dt = (d = function (t) {
        return "Symbol(" + String(void 0 === t ? "" : t) + ")_" + (++ut + pt).toString(36)
    }, u.exports), ht = d, ft = dt("keys"), mt = m, vt = $, gt = O, yt = (k = bt, x = function (t) {
        return ft[t] || (ft[t] = ht(t))
    }, dt = H = {}, "Object already initialized"), bt = c.WeakMap;
    ct = g || k.state ? (nt = k.state || (k.state = new bt), rt = nt.get, ot = nt.has, it = nt.set, at = function (t, e) {
        if (ot.call(nt, t)) throw new TypeError(yt);
        return e.facade = t, it.call(nt, t, e), e
    }, st = function (t) {
        return rt.call(nt, t) || {}
    }, function (t) {
        return ot.call(nt, t)
    }) : (dt[lt = x("state")] = !0, at = function (t, e) {
        if (gt(t, lt)) throw new TypeError(yt);
        return e.facade = t, vt(t, lt, e), e
    }, st = function (t) {
        return gt(t, lt) ? t[lt] : {}
    }, function (t) {
        return gt(t, lt)
    });
    var wt = c, _t = $, St = O, Et = f, Mt = p, Pt = (k = {
        set: at, get: st, has: ct, enforce: function (t) {
            return ct(t) ? st(t) : at(t, {})
        }, getterFor: function (t) {
            return function (e) {
                var n;
                if (!mt(e) || (n = st(e)).type !== t) throw TypeError("Incompatible receiver, " + t + " required");
                return n
            }
        }
    }).get, xt = k.enforce, Rt = String(String).split("String");

    function Ct(t) {
        return "function" == typeof t ? t : void 0
    }

    (Y.exports = function (t, e, n, r) {
        var o = !!r && !!r.unsafe, i = !!r && !!r.enumerable, a = !!r && !!r.noTargetGet;
        "function" == typeof n && ("string" != typeof e || St(n, "name") || _t(n, "name", e), (r = xt(n)).source || (r.source = Rt.join("string" == typeof e ? e : ""))), t !== wt ? (o ? !a && t[e] && (i = !0) : delete t[e], i ? t[e] = n : _t(t, e, n)) : i ? t[e] = n : Et(e, n)
    })(Function.prototype, "toString", (function () {
        return "function" == typeof this && Pt(this).source || Mt(this)
    }));
    var jt = bt = c, Ot = c, Ht = (dt = function (t, e) {
            return arguments.length < 2 ? Ct(jt[t]) || Ct(Ot[t]) : jt[t] && jt[t][e] || Ot[t] && Ot[t][e]
        }, x = {}, Math.ceil), Lt = Math.floor, Tt = p = function (t) {
            return isNaN(t = +t) ? 0 : (0 < t ? Lt : Ht)(t)
        }, kt = Math.min, At = (k = function (t) {
            return 0 < t ? kt(Tt(t), 9007199254740991) : 0
        }, p), Ut = Math.max, Dt = Math.min, It = M, Bt = k, qt = (p = {
            includes: (p = function (t) {
                return function (e, n, r) {
                    var o, i = It(e), a = Bt(i.length), s = function (t, e) {
                        return (t = At(t)) < 0 ? Ut(t + e, 0) : Dt(t, e)
                    }(r, a);
                    if (t && n != n) {
                        for (; s < a;) if ((o = i[s++]) != o) return !0
                    } else for (; s < a; s++) if ((t || s in i) && i[s] === n) return t || s || 0;
                    return !t && -1
                }
            })(!0), indexOf: p(!1)
        }, O), Jt = M, Ft = p.indexOf, Nt = H, zt = M = function (t, e) {
            var n, r = Jt(t), o = 0, i = [];
            for (n in r) !qt(Nt, n) && qt(r, n) && i.push(n);
            for (; e.length > o;) qt(r, n = e[o++]) && (~Ft(i, n) || i.push(n));
            return i
        },
        Wt = (p = ["constructor", "hasOwnProperty", "isPrototypeOf", "propertyIsEnumerable", "toLocaleString", "toString", "valueOf"]).concat("length", "prototype");
    x.f = Object.getOwnPropertyNames || function (t) {
        return zt(t, Wt)
    }, (H = {}).f = Object.getOwnPropertySymbols;
    var Qt = x, Vt = H, Xt = Gt, Gt = dt("Reflect", "ownKeys") || function (t) {
            var e = Qt.f(Xt(t)), n = Vt.f;
            return n ? e.concat(n(t)) : e
        }, $t = O, Yt = Gt, Kt = l, Zt = F, te = ir, ee = /#|\.prototype\./, ne = (F = function (t, e) {
            return (t = re[ne(t)]) == ie || t != oe && ("function" == typeof e ? te(e) : !!e)
        }).normalize = function (t) {
            return String(t).replace(ee, ".").toLowerCase()
        }, re = F.data = {}, oe = F.NATIVE = "N", ie = F.POLYFILL = "P", ae = c, se = l.f, ce = $, le = Y.exports, ue = f,
        pe = function (t, e) {
            for (var n = Yt(e), r = Zt.f, o = Kt.f, i = 0; i < n.length; i++) {
                var a = n[i];
                $t(t, a) || r(t, a, o(e, a))
            }
        }, de = F, he = (Y = function (t, e) {
            var n, r, o, i = t.target, a = t.global, s = t.stat,
                c = a ? ae : s ? ae[i] || ue(i, {}) : (ae[i] || {}).prototype;
            if (c) for (n in e) {
                if (r = e[n], o = t.noTargetGet ? (o = se(c, n)) && o.value : c[n], !de(a ? n : i + (s ? "." : "#") + n, t.forced) && void 0 !== o) {
                    if (typeof r == typeof o) continue;
                    pe(r, o)
                }
                (t.sham || o && o.sham) && ce(r, "sham", !0), le(c, n, r, t)
            }
        }, f = function (t, e, n) {
            if (function (t) {
                if ("function" != typeof t) throw TypeError(String(t) + " is not a function")
            }(t), void 0 === e) return t;
            switch (n) {
                case 0:
                    return function () {
                        return t.call(e)
                    };
                case 1:
                    return function (n) {
                        return t.call(e, n)
                    };
                case 2:
                    return function (n, r) {
                        return t.call(e, n, r)
                    };
                case 3:
                    return function (n, r, o) {
                        return t.call(e, n, r, o)
                    }
            }
            return function () {
                return t.apply(e, arguments)
            }
        }, fe), fe = (F = Array.isArray || function (t) {
            return "Array" == he(t)
        }, dt("navigator", "userAgent") || "");

    function me(t, e) {
        var n;
        return new (void 0 === (n = Pe(t) && ("function" == typeof (n = t.constructor) && (n === Array || Pe(n.prototype)) || Me(n) && null === (n = n[xe])) ? void 0 : n) ? Array : n)(0 === e ? 0 : e)
    }

    (dt = (dt = (dt = c.process) && dt.versions) && dt.v8) ? ye = (ge = dt.split("."))[0] < 4 ? 1 : ge[0] + ge[1] : fe && (!(ge = fe.match(/Edge\/(\d+)/)) || 74 <= ge[1]) && (ge = fe.match(/Chrome\/(\d+)/)) && (ye = ge[1]);
    var ve = ye && +ye, ge = (fe = ir, !!Object.getOwnPropertySymbols && !fe((function () {
            var t = Symbol();
            return !String(t) || !(Object(t) instanceof Symbol) || !Symbol.sham && ve && ve < 41
        }))), ye = ge && !Symbol.sham && "symbol" == typeof Symbol.iterator, be = (fe = c, u = u.exports, O),
        we = (d = d, ge), _e = (ye = ye, u("wks")), Se = fe.Symbol, Ee = ye ? Se : Se && Se.withoutSetter || d, Me = m,
        Pe = F, xe = function (t) {
            return be(_e, t) && (we || "string" == typeof _e[t]) || (we && be(Se, t) ? _e[t] = Se[t] : _e[t] = Ee("Symbol." + t)), _e[t]
        }("species"), Re = f, Ce = _, je = v, Oe = k, He = [].push, Le = (k = {
            forEach: (k = function (t) {
                var e = 1 == t, n = 2 == t, r = 3 == t, o = 4 == t, i = 6 == t, a = 7 == t, s = 5 == t || i;
                return function (c, l, u, p) {
                    for (var d, h, f = je(c), m = Ce(f), v = Re(l, u, 3), g = Oe(m.length), y = 0, b = (p = p || me, e ? p(c, g) : n || a ? p(c, 0) : void 0); y < g; y++) if ((s || y in m) && (h = v(d = m[y], y, f), t)) if (e) b[y] = h; else if (h) switch (t) {
                        case 3:
                            return !0;
                        case 5:
                            return d;
                        case 6:
                            return y;
                        case 2:
                            He.call(b, d)
                    } else switch (t) {
                        case 4:
                            return !1;
                        case 7:
                            He.call(b, d)
                    }
                    return i ? -1 : r || o ? o : b
                }
            })(0), map: k(1), filter: k(2), some: k(3), every: k(4), find: k(5), findIndex: k(6), filterOut: k(7)
        }, ir), Te = k.forEach;
    k = function (t, e) {
        var n = [].forEach;
        return !!n && Le((function () {
            n.call(null, (function () {
                throw 1
            }), 1)
        }))
    }() ? [].forEach : function (t) {
        return Te(this, t, 1 < arguments.length ? arguments[1] : void 0)
    };
    Y({target: "Array", proto: !0, forced: [].forEach != k}, {forEach: k});
    var ke, Ae, Ue, De = c, Ie = f, Be = Function.call;

    function qe(t, e) {
        if (!t) throw new TypeError("Invalid argument");
        var n = document.implementation.createHTMLDocument("");
        e && ((r = n.createElement("base")).href = e, n.head.appendChild(r));
        var r = n.createElement("a");
        if (r.href = t, n.body.appendChild(r), ":" === r.protocol || !/:/.test(r.href)) throw new TypeError("Invalid URL");
        Object.defineProperty(this, "_anchorElement", {value: r})
    }

    Ie(Be, De.Array.prototype.forEach, void 0), (s = void 0 !== s ? s : "undefined" != typeof window ? window : "undefined" != typeof self ? self : s) && ("href" in (null !== (Ue = null === (Ue = s.URL) || void 0 === Ue ? void 0 : Ue.prototype) && void 0 !== Ue ? Ue : {}) || (qe.prototype = {
        toString: function () {
            return this.href
        }, get href() {
            return this._anchorElement.href
        }, set href(t) {
            this._anchorElement.href = t
        }, get protocol() {
            return this._anchorElement.protocol
        }, set protocol(t) {
            this._anchorElement.protocol = t
        }, get host() {
            return this._anchorElement.host
        }, set host(t) {
            this._anchorElement.host = t
        }, get hostname() {
            return this._anchorElement.hostname
        }, set hostname(t) {
            this._anchorElement.hostname = t
        }, get port() {
            return this._anchorElement.port
        }, set port(t) {
            this._anchorElement.port = t
        }, get pathname() {
            return this._anchorElement.pathname
        }, set pathname(t) {
            this._anchorElement.pathname = t
        }, get search() {
            return this._anchorElement.search
        }, set search(t) {
            this._anchorElement.search = t
        }, get hash() {
            return this._anchorElement.hash
        }, set hash(t) {
            this._anchorElement.hash = t
        }
    }, ke = s.URL || s.webkitURL || s.mozURL, qe.createObjectURL = function (t) {
        return ke.createObjectURL(t)
    }, qe.revokeObjectURL = function (t) {
        return ke.revokeObjectURL(t)
    }, Object.defineProperty(qe.prototype, "toString", {enumerable: !1}), s.URL = qe)), Element.prototype.addEventListener || (Ae = [], Ue = function (t, e) {
        function n(t) {
            t.target = t.srcElement, t.currentTarget = i, void 0 !== e.handleEvent ? e.handleEvent(t) : e.call(i, t)
        }

        var r, o, i = this;
        "DOMContentLoaded" === t ? (r = function (t) {
            "complete" === document.readyState && n(t)
        }, document.attachEvent("onreadystatechange", r), Ae.push({
            object: this,
            type: t,
            listener: e,
            wrapper: r
        }), "complete" === document.readyState && ((o = new Event).srcElement = window, r(o))) : (this.attachEvent("on" + t, n), Ae.push({
            object: this,
            type: t,
            listener: e,
            wrapper: n
        }))
    }, s = function (t, e) {
        for (var n = 0; n < Ae.length;) {
            var r = Ae[n];
            if (r.object === this && r.type === t && r.listener === e) {
                "DOMContentLoaded" === t ? this.detachEvent("onreadystatechange", r.wrapper) : this.detachEvent("on" + t, r.wrapper), Ae.splice(n, 1);
                break
            }
            ++n
        }
    }, Element.prototype.addEventListener = Ue, Element.prototype.removeEventListener = s, HTMLDocument && !HTMLDocument.prototype.addEventListener && (HTMLDocument.prototype.addEventListener = Ue, HTMLDocument.prototype.removeEventListener = s), Window && !Window.prototype.addEventListener && (Window.prototype.addEventListener = Ue, Window.prototype.removeEventListener = s));
    var Je = M, Fe = p, Ne = (p = Object.keys || function (t) {
        return Je(t, Fe)
    }, v), ze = p;

    function We(t) {
        return "object" == typeof t && null !== t && !Xe(t)
    }

    function Qe(t) {
        return "function" == typeof t
    }

    function Ve(t) {
        return "[object String]" === Object.prototype.toString.call(t)
    }

    function Xe(t) {
        return "[object Array]" === Object.prototype.toString.call(t)
    }

    function Ge(t, e) {
        return Object.prototype.hasOwnProperty.call(t, e)
    }

    function $e(t, e) {
        if (We(t)) for (var n in t) Ge(t, n) && e.call(null, n, t[n])
    }

    function Ye() {
        for (var t = [], e = 0; e < arguments.length; e++) t[e] = arguments[e];
        for (var n = {}, r = 0; r < t.length;) n = tn(n, t[r]), r++;
        return n
    }

    Y({
        target: "Object", stat: !0, forced: ir((function () {
            ze(1)
        }))
    }, {
        keys: function (t) {
            return ze(Ne(t))
        }
    }), bt.Object.keys;
    var Ke = Object.getPrototypeOf ? Object.getPrototypeOf({}) : null;

    function Ze(t) {
        return Object.getPrototypeOf ? Object.getPrototypeOf(t) === Ke : t.constructor === Object
    }

    function tn(t, e) {
        var r, o = n({}, t);
        for (r in e) Object.prototype.hasOwnProperty.call(e, r) && void 0 !== e[r] && (We(e[r]) && Ze(e[r]) ? o[r] = tn(We(t[r]) ? t[r] : {}, e[r]) : Xe(t[r]) || Xe(e[r]) ? o[r] = function t(e, n) {
            return e = Xe(e) ? e : [], n = Xe(n) ? n : [], Array.prototype.concat.call(e, n).map((function (e) {
                return e instanceof RegExp || !(Xe(e) || We(e) && Ze(e)) ? e : Xe(e) ? t([], e) : tn({}, e)
            }))
        }(t[r], e[r]) : o[r] = e[r]);
        return o
    }

    function en(t, e) {
        if (Xe(t) && 0 !== t.length) for (var n = 0; n < t.length;) {
            if (t[n] === e) return 1;
            n++
        }
    }

    function nn() {
        return !!We(window)
    }

    function rn(t) {
        var e = document.createElement("a");
        return e.href = t, "/" !== (t = e.pathname || "/")[0] && (t = "/" + t), {
            href: e.href,
            protocol: e.protocol.slice(0, -1),
            hostname: e.hostname,
            host: e.host,
            search: e.search,
            pathname: t,
            hash: e.hash
        }
    }

    var on = new RegExp("(" + ["cookie", "auth", "jwt", "token", "key", "ticket", "secret", "credential", "session", "password"].join("|") + ")", "i"),
        an = new RegExp("(" + ["bearer", "session"].join("|") + ")", "i"), sn = function (t, e) {
            return !(!t || !e) && (on.test(t) || an.test(e))
        };

    function cn() {
    }

    function ln(t) {
        var e = "[object String]" === Object.prototype.toString.call(t);
        return t ? e ? t.length : ArrayBuffer && t instanceof ArrayBuffer ? t.byteLength : window.Blob && t instanceof Blob ? t.size : t.length || 0 : 0
    }

    function un(t) {
        if (!t) return "";
        if (!Qe(t.forEach)) return "";
        var e = [];
        return t.forEach((function (t) {
            var n;
            Xe(t) && t[0] && !sn(t[0], t[1]) && (n = t[0], t = t[1] || "", e.push(n + ": " + t))
        })), e.join("\r\n")
    }

    function pn(t) {
        var e = {}, n = rn(t);
        return e.ax_protocol = n.protocol, e.ax_domain = n.hostname, e.ax_path = n.pathname, e.ax_url = (n.href || t).split("?")[0], e.ax_search = n.search, e.ax_href = n.href, e
    }

    function dn(t) {
        return Xe(t) && t.length ? function (t) {
            for (var e = [], n = t.length, r = 0; r < n; r++) {
                var o = t[r];
                Ve(o) ? e.push(o.replace(/([.*+?^=!:${}()|[\]/\\])/g, "\\$1")) : o && o.source && e.push(o.source)
            }
            return new RegExp(e.join("|"), "i")
        }(t) : null
    }

    var hn = function (t) {
        var e, n;
        return void 0 === t && (t = window), nn() && null !== (e = t.screen) && void 0 !== e && e.width && null !== (n = t.screen) && void 0 !== n && n.height ? t.screen.width + "x" + t.screen.height : "0x0"
    }, fn = function () {
        var t = navigator.connection || navigator.mozConnection || navigator.webkitConnection;
        return t && Ve(t.effectiveType) ? t.effectiveType : "unknown"
    };

    function mn(t) {
        return !!t && Math.random() < Number(t)
    }

    var vn = (gn.post = function (t, e, n) {
        var r = n && n.success || cn, o = n && n.fail || cn;
        (n = new XMLHttpRequest).open("POST", t, !0), n.setRequestHeader("Content-Type", "application/json"), n.send(JSON.stringify(e)), n.onload = function () {
            try {
                var t;
                this.responseText ? (t = JSON.parse(this.responseText), r(t)) : r({})
            } catch (t) {
                o()
            }
        }, n.onerror = function () {
            o()
        }, n.onabort = function () {
            o()
        }
    }, gn.get = function (t, e) {
        var n = e && e.success || cn, r = e && e.fail || cn, o = e && e.getResponse || cn,
            i = e && e.getResponseText || cn, a = new XMLHttpRequest;
        e && e.withCredentials && (a.withCredentials = e.withCredentials), a.open("GET", t), a.send(), a.onload = function () {
            o(null == this ? void 0 : this.response), i(this.responseText);
            try {
                var t;
                this.responseText ? (t = JSON.parse(this.responseText), n(t)) : n({})
            } catch (t) {
                r()
            }
        }, a.onerror = function () {
            r()
        }, a.onabort = function () {
            r()
        }
    }, gn.prototype.getCommonParams = function () {
        return {timestamp: Date.now()}
    }, gn);

    function gn(t) {
        var e = this;
        this.postEvent = function (t) {
            t = n(n({}, t), e.getCommonParams()), gn.post(e.url, t)
        }, this.getEvent = function (t) {
            t = function (t) {
                t = function (t) {
                    if (!We(t)) return {};
                    var e = {};
                    return $e(t, (function (t, n) {
                        We(n) || Xe(n) ? e[t] = JSON.stringify(n) : e[t] = n
                    })), e
                }(t), e = {}, $e(t, (function (t, n) {
                    e[encodeURIComponent(t)] = encodeURIComponent(n)
                }));
                var e, n = [];
                return $e(t = e, (function (t, e) {
                    n.push(t + "=" + e)
                })), n.join("&")
            }(n(n({}, t), e.getCommonParams())), t = e.url + "?" + t, gn.get(t)
        }, this.getURL = function () {
            return e.url
        }, this.options = t, this.url = this.options.reportURL
    }

    function yn(t, e, n) {
        if (1 < (n = void 0 === n ? "0" : n).length) throw new TypeError("expect the length of pad to be 1, but got " + t.length);
        return t.length >= e ? t : "" + Array.from({length: e - t.length}).fill(n).join("") + t
    }

    function bn(t) {
        return t ? Math.floor(Math.random() * Math.pow(10, 12)).toString(16).substr(0, t) : ""
    }

    var wn = "x-tt-trace-id", _n = "x-tt-trace-host", Sn = "x-tt-logid";

    function En(t, e, n) {
        return void 0 === n && (n = "01"), "00-" + (e = "" + yn((Date.now() % 4294967295).toString(16), 8) + function (t) {
            if ("number" == typeof t) n = t; else {
                var e = t.replace(/-/g, "");
                if (!/^[0-9]+$/.test(e)) return 18 < e.length ? "18" + bn(18) : (t = 22 - e.length - 4, "" + yn("" + e.length, 2) + e + bn(t));
                n = parseInt(e, 10)
            }
            var n = 22 - (e = n.toString(16)).length - 4;
            return "" + yn("" + e.length, 2) + e + bn(n)
        }(t) + ("string" == typeof e ? bn(4) : yn(e.toString(16), 4))) + "-" + e.substr(0, 16) + "-" + n
    }

    function Mn() {
        var t = function () {
            for (var t = new Array(16), e = 0, n = 0; n < 16; n++) 0 == (3 & n) && (e = 4294967296 * Math.random()), t[n] = e >>> ((3 & n) << 3) & 255;
            return t
        }();
        return t[6] = 15 & t[6] | 64, t[8] = 63 & t[8] | 128, function (t) {
            for (var e = [], n = 0; n < 256; ++n) e[n] = (n + 256).toString(16).substr(1);
            var r = 0, o = e;
            return [o[t[r++]], o[t[r++]], o[t[r++]], o[t[r++]], "-", o[t[r++]], o[t[r++]], "-", o[t[r++]], o[t[r++]], "-", o[t[r++]], o[t[r++]], "-", o[t[r++]], o[t[r++]], o[t[r++]], o[t[r++]], o[t[14]], o[t[15]]].join("")
        }(t)
    }

    function Pn() {
    }

    var xn = ["/log/sentry/", "/monitor_browser/collect"], Rn = "MONITOR_WEB_ID", Cn = Mn(), jn = Mn();

    function On(t, e) {
        var n, r;
        void 0 === e && (e = Pn), t && Ve(t) && nn() && We(document) && Qe(document.createElement) && (n = document.createElement("script"), r = document.head, We(n) && We(r) && (n.src = t, n.async = !0, n.crossOrigin = "anonymous", n.onload = function () {
            e()
        }, Qe(r.appendChild) && r.appendChild(n)))
    }

    function Hn() {
        if (We(window)) return window
    }

    function Ln() {
        if (Hn() && We(window.performance)) return window.performance
    }

    function Tn(t, e, n) {
        var r = t.commonParams, o = t.ajaxMonitorOptions;
        r = (t = r || {}).bid, t = t.web_id, o = (o || {}).sampleHitTrace, e = e, n = rn(n = n), e = rn(e), e = n.protocol === e.protocol && n.host === e.host;
        return r && t && o && e
    }

    function kn(t, e, n) {
        return function () {
            for (var r = [], o = 0; o < arguments.length; o++) r[o] = arguments[o];
            if (!t) return cn;
            var s = t[e], c = n.apply(void 0, a([s], i(r))), l = c;
            return Qe(l) && (l = function () {
                for (var t = [], e = 0; e < arguments.length; e++) t[e] = arguments[e];
                try {
                    return c.apply(this, t)
                } catch (e) {
                    return Qe(s) && s.apply(this, t)
                }
            }), t[e] = l, function (n) {
                n && l !== t[e] || (t[e] = s)
            }
        }
    }

    function An(t, e) {
        return t instanceof e
    }

    function Un(t, e) {
        return t instanceof e
    }

    function Dn(t) {
        return null !== (t = null === (t = t.ajaxMonitorOptions) || void 0 === t ? void 0 : t.errorRequestCollect) && void 0 !== t && t
    }

    function In(t) {
        function e() {
            return Object.keys(n).forEach((function (t) {
                return !(t in r) && Object.defineProperty(r, t, {
                    get: function () {
                        return n[t]
                    }
                })
            }))
        }

        var n = t, r = {};
        return e(), [r, function (t) {
            n = Ye({}, n, t), e()
        }]
    }

    function Bn(t, e) {
        var n, r;
        return void 0 === e && (e = Ln()), (0, i([r = (n = e) && n.timing || void 0, function () {
            return n && n.now ? n.now() : (Date.now ? Date.now() : +new Date) - (r && r.navigationStart || 0)
        }, function (t) {
            var e = (n || {}).getEntriesByType;
            return Qe(e) && e.call(n, t) || []
        }, function () {
            var t = (n || {}).clearResourceTimings;
            Qe(t) && t.call(n)
        }, function (t) {
            var e = (n || {}).getEntriesByName;
            return Qe(e) && e.call(n, t) || []
        }], 5)[4])(t).pop()
    }

    function qn(t, e, r, o) {
        var i = t._method, a = t._requestHeaders, s = t._url, c = t._start, l = t._data;
        i = {
            ev_type: "ajax",
            ax_status: (t.status || 0).toString(),
            ax_type: i,
            ax_request_header: un(a || []),
            ax_domain: "",
            ax_duration: 0,
            ax_path: "",
            ax_protocol: "",
            ax_response_header: "",
            ax_size: 0,
            ax_url: "",
            ax_href: ""
        };
        return "function" == typeof t.getAllResponseHeaders && (i.ax_response_header = function (t) {
            if (Ve(t)) {
                var e = t.split("\r\n"), n = [];
                return e.forEach((function (t, e) {
                    t && Qe(t.split) && (t = t.split(": "), sn(t[0], t[1]) || n.push([t[0], t[1]]))
                })), un(n)
            }
            return We(t) ? un(Object.keys(t).filter((function (e) {
                return !sn(e, t[e])
            })).map((function (e) {
                return [e, t[e]]
            }))) : t
        }(t.getAllResponseHeaders())), !Tn(e, s, o) || (r = r || t.getResponseHeader(wn)) && (i.trace_id = r, i.trace_host = t.getResponseHeader(_n) || void 0, i.log_id = t.getResponseHeader(Sn) || void 0), i.ax_duration = Date.now() - c, 200 === t.status && (i.ax_size = function (t) {
            if ("" === t.responseType || "text" === t.responseType) return ln(t.responseText);
            if (t.response) return ln(t.response);
            try {
                return ln(t.responseText)
            } catch (t) {
                return 0
            }
        }(t)), (i = n(n({}, i), pn(s))).resource_timing = Bn(i.ax_href), t = i.ax_status, Dn(e) && "2" !== t[0] && "3" !== t[0] && (i.ax_request = {
            body: l ? "" + l : void 0,
            search: rn(s).search,
            content_type: a && (null === (a = a.find((function (t) {
                return "content-type" === t[0].toLowerCase()
            }))) || void 0 === a ? void 0 : a[1])
        }), i
    }

    function Jn(t) {
        return function () {
            for (var e = [], n = 0; n < arguments.length; n++) e[n] = arguments[n];
            var r = (o = i(e, 2))[0], o = o[1];
            return this._url = o || "", this._method = r && r.toLowerCase() || "", t.apply(this, e)
        }
    }

    function Fn(t) {
        return function () {
            for (var e = [], n = 0; n < arguments.length; n++) e[n] = arguments[n];
            return this._requestHeaders = this._requestHeaders || [], this._requestHeaders.push(e), t && t.apply(this, e)
        }
    }

    function Nn(t, e, n, r) {
        var o = null;
        return function () {
            for (var i, a, s = [], c = 0; c < arguments.length; c++) s[c] = arguments[c];
            return Tn(e, this._url, r) && (i = (a = e.commonParams || {}).bid, a = a.web_id, i && a && (o = En(a, i), this.setRequestHeader(wn, o))), Qn(this)(e, o, n, r), this._start = Date.now(), this._data = null == s ? void 0 : s[0], t.apply(this, s)
        }
    }

    function zn(t, e, n) {
        var r = "";
        r = An(t, n) ? t.url : t;
        return (e = void 0 !== e && e) ? r : Ve(r) ? r.split("?")[0] : r
    }

    var Wn = "AjaxMonitor", Qn = function (t) {
        return kn(t, "onreadystatechange", (function (e, n, r, o, i) {
            return function () {
                for (var a = [], s = 0; s < arguments.length; s++) a[s] = arguments[s];
                return 4 === this.readyState && o && o({
                    name: Wn,
                    type: "get",
                    event: qn(t, n, r, i)
                }), e && e.apply(this, a)
            }
        }))
    };

    function Vn(t, e) {
        if (!t) return "";
        var n = [];
        return Un(t, e) ? t.forEach((function (t, e) {
            n.push([e, t])
        })) : Xe(t) ? n.push.apply(n, a([], i(t))) : n.push.apply(n, a([], i(Object.entries(t)))), un(n)
    }

    function Xn(t, e, r, o, a, s) {
        return function (c, l) {
            void 0 === l && (l = {});
            var u = zn(c, void 0, a);
            if (!function (t) {
                if (Ve(t)) {
                    var e = i(t.split(":"), 2);
                    t = e[0];
                    return !e[1] || "http" === t || "https" === t
                }
            }(u)) return t(c, l);
            var p, d, h, f = (m = e.commonParams || {}).bid, m = m.web_id, v = Date.now(),
                g = {ev_type: "ajax", ax_size: 0};
            try {
                Tn(e, u, s) && (l.headers || (l.headers = []), f && m && (g.trace_id = En(f, m), p = l.headers, d = wn, h = g.trace_id, Un(p, o) ? p.append(d, h) : Xe(p) ? p.push([d, h]) : p[d] = h))
            } catch (p) {
            }

            function y() {
                (function (t, e, n, r, o, i) {
                    var a = e.ax_status;
                    Dn(t) && "2" !== a[0] && "3" !== a[0] && (a = zn(n, !0, o), e.ax_request = {
                        body: null === (e = r, e = An(n = n, o) ? n.body : null == e ? void 0 : e.body) || void 0 === e ? void 0 : e.toString(),
                        search: rn(a).search,
                        content_type: function (t, e, n) {
                            if (t) return Un(t, n) ? t.get(e) || void 0 : Xe(t) ? null == (n = t.find((function (t) {
                                return e === t[0]
                            }))) ? void 0 : n[1] : t[e]
                        }(r.headers, "Content-Type", i)
                    })
                })(e, g, c, l, a, o), null != r && r({name: Gn, type: "get", event: g})
            }

            return g.ax_type = function (t, e, n) {
                return e = e && e.method || "get", (e = An(t, n) && t.method || e).toLowerCase()
            }(c, l, a), g = n(n({}, g), pn(zn(c, !0, a))), t(c, l).then((function (t) {
                var n, r, i, p, d, h;
                try {
                    g.ax_status = (t.status || 0).toString(), g.ax_duration = Date.now() - v, g.ax_response_header = Vn(t.headers, o), g.ax_request_header = Vn((r = c, i = l, d = a, h = new (p = o), An(r, d) && r.headers && Qe(r.headers.forEach) && r.headers.forEach((function (t, e) {
                        h.append(e, t)
                    })), i.headers && new p(i.headers).forEach((function (t, e) {
                        h.append(e, t)
                    })), h), o), g.resource_timing = Bn(g.ax_href);
                    var f = function (e, n) {
                        t.headers.has(e) && (g[n] = t.headers.get(e) || void 0)
                    };
                    Qe(null === (n = t.headers) || void 0 === n ? void 0 : n.has) && (t.headers.has("content-length") && (g.ax_size = Number(t.headers.get("content-length")) || 0), Tn(e, u, s) && (f(_n, "trace_host"), f(wn, "trace_id"), f(Sn, "log_id"))), y()
                } catch (n) {
                }
                return t
            }), (function (t) {
                try {
                    g.ax_status = "0", g.ax_duration = Date.now() - v, g.resource_timing = Bn(g.ax_href), y()
                } catch (t) {
                }
                return Promise.reject(t)
            }))
        }
    }

    var Gn = "FetchMonitor", $n = ["SCRIPT", "STYLE", "META", "HEAD"], Yn = function (t, e, n, r) {
        if (!t || -1 < r.indexOf(t.tagName)) return 0;
        var o = t.children;
        if ((o = [].slice.call(void 0 === o ? [] : o).reduceRight((function (t, n) {
            return t + Yn(n, e + 1, 0 < t, r)
        }), 0)) <= 0 && !n) {
            if (!Qe(t.getBoundingClientRect)) return 0;
            if (n = t.getBoundingClientRect() || {}, t = n.top, n = n.height, t > window.innerHeight || n <= 0) return 0
        }
        return o + 1 + .5 * e
    }, Kn = "PathMonitor", Zn = (tr.prototype.setup = function (t) {
        nn() && (this.currentURL = location.href, this.callback = t, this.start())
    }, tr.prototype.onChange = function (t) {
        this.onPathChange = t
    }, tr.prototype.start = function () {
        this.monitorHashChange(), nn() && We(window.history) && Qe(window.history.pushState) && Qe(window.history.replaceState) && (this.monitorPopState(), this.monitorPushState(), this.monitorReplaceState())
    }, tr.prototype.monitorPopState = function () {
        var t = this;
        window.addEventListener("popstate", (function () {
            t.handleStateChange(t.currentURL, location.href), t.replaceCurrentURL(location.href)
        }))
    }, tr.prototype.monitorHashChange = function () {
        var t = this;
        window.addEventListener("hashchange", (function () {
            var e = t.parseHash(location.hash);
            e && t.pathChangeHandler(t.getPath(e), "hash"), t.replaceCurrentURL(location.href)
        }))
    }, tr.prototype.monitorPushState = function () {
        var t = this, e = window.history.pushState;
        e && (window.history.pushState = function () {
            for (var n = [], r = 0; r < arguments.length; r++) n[r] = arguments[r];
            try {
                return e.apply(this, n)
            } finally {
                t.handleHistoryStateChange.apply(t, a([], i(n)))
            }
        })
    }, tr.prototype.monitorReplaceState = function () {
        var t = this, e = window.history.replaceState;
        e && (window.history.replaceState = function () {
            for (var n = [], r = 0; r < arguments.length; r++) n[r] = arguments[r];
            try {
                return e.apply(this, n)
            } finally {
                t.handleHistoryStateChange.apply(t, a([], i(n)))
            }
        })
    }, tr.prototype.pathChangeHandler = function (t, e, n) {
        Ve(t) && Qe(this.callback) && (this.onPathChange && this.onPathChange({
            path: t,
            type: e,
            url: n
        }), this.callback({name: this.name, type: "get", event: {ev_type: "pageview", path: t, type: e}}))
    }, tr.prototype.handleHistoryStateChange = function (t, e, n) {
        n && (this.handleStateChange(this.currentURL, n), this.replaceCurrentURL(n))
    }, tr.prototype.handleStateChange = function (t, e) {
        t = this.parseURL(t), (e = this.parseURL(e)) && t && (e.pathname === t.pathname ? e.hash !== t.hash && this.pathChangeHandler(this.getPath(e.hash), "hash", e) : this.pathChangeHandler(this.getPath(e.pathname), "pathname", e))
    }, tr.prototype.parseURL = function (t) {
        var e = null;
        if (!t) return e;
        if ("string" != typeof t) return e;
        if (t.match(/^\/[^\/]/)) return new URL(location.protocol + "//" + location.host + t);
        try {
            return new URL(t)
        } catch (t) {
            return e
        }
    }, tr.prototype.parseHash = function (t) {
        return t && "string" == typeof t ? t.replace(/^#/, "") : "/"
    }, tr.prototype.getPath = function (t) {
        return t = t && "string" == typeof t ? t.replace(/^(https?:)?\/\//, "").replace(/\?.*$/, "") : "", this.parseHash(t)
    }, tr.prototype.replaceCurrentURL = function (t) {
        this.currentURL !== t && (this.currentURL = t)
    }, tr.monitorName = Kn, tr);

    function tr() {
        this.name = Kn, this.callback = cn, this.onPathChange = cn, this.currentURL = ""
    }

    var er = (ir = function (t, e, n, r) {
        var o = (s.prototype.setup = function (t) {
            this.monitor = function (t, e, n, r) {
                void 0 === e && (e = {}), void 0 === r && (r = []);
                try {
                    var o = t.apply(void 0, a([], i(r)));
                    return o && o(e, n) || []
                } catch (t) {
                    return []
                }
            }(e, this.props, t, r)
        }, s.monitorName = t, s);

        function s(e) {
            this.props = e, this.name = t
        }

        return n.forEach((function (t, e) {
            return o.prototype[t] = function () {
                for (var t, n, r = [], o = 0; o < arguments.length; o++) r[o] = arguments[o];
                return null === (n = null === (t = this.monitor) || void 0 === t ? void 0 : t[e]) || void 0 === n ? void 0 : n.call.apply(n, a([t], i(r)))
            }
        })), o
    })(Wn, (function (t, e) {
        void 0 === t && (t = function () {
            if (Qe(XMLHttpRequest)) return XMLHttpRequest
        }()), void 0 === e && (e = null === location || void 0 === location ? void 0 : location.href);
        var n = t && t.prototype;
        if (n) return function (t, r) {
            t = (o = i(In(t), 2))[0];
            var o = o[1];
            return kn(n, "open", Jn)(), kn(n, "send", Nn)(t, r, e || ""), kn(n, "setRequestHeader", Fn)(), [o]
        }
    }), ["updateConfig"]), nr = ir(Gn, (function (t, e, n, r) {
        if (void 0 === t && (t = function () {
            try {
                return new Headers, new Request(""), new Response, window.fetch
            } catch (t) {
            }
        }() && Hn()), void 0 === e && (e = window.Headers), void 0 === n && (n = window.Request), void 0 === r && (r = null === location || void 0 === location ? void 0 : location.href), t && e && n) return function (o, a) {
            o = (s = i(In(o), 2))[0];
            var s = s[1];
            return kn(t, "fetch", Xn)(o, a, e, n, r || ""), [s]
        }
    }), ["updateConfig"]);
    rr.prototype.sendEvent = function (t) {
        (t = this.getEventToBeSent(t)) && this.idleSendEvent(t)
    }, rr.prototype.getEventToBeSent = function (t) {
        if (t = this._modifyEvent(t), this._shouldSend(t)) return t
    }, rr.prototype._modifyEvent = function (t) {
        return t
    }, rr.prototype._shouldSend = function (t) {
        return !0
    }, rr.prototype._send = function (t) {
    }, rr.prototype.idleSendEvent = function (t) {
        this._send(t)
    }, bt = rr;

    function rr() {
    }

    var or, ir = (e(ar, or = bt), Object.defineProperty(ar.prototype, "ready", {
        get: function () {
            return this.isReady
        }, set: function (t) {
            this.isReady = t, this.isReady && this._uploadQueue()
        }, enumerable: !1, configurable: !0
    }), ar.prototype._send = function (t) {
        var e = this;
        null != (t = this.buildParams(t)) && (this.reportQueue.push(t), this.isReady && (this.reportQueue.length >= this.batchReportLength && this._uploadQueue(), this.batchReportTimeout && clearTimeout(this.batchReportTimeout), this.batchReportTimeout = setTimeout((function () {
            e._uploadQueue()
        }), this.batchReportWait)))
    }, ar.prototype._uploadQueue = function () {
        var t;
        this.reportQueue.length && this.ready && (t = {
            ev_type: "batch",
            list: this.reportQueue
        }, this.reportQueue = [], this._request({event: t, type: "post"}))
    }, ar.prototype._request = function (t) {
    }, ar);

    function ar(t) {
        var e, n = or.call(this) || this;
        return n.reportQueue = [], n.isReady = !0, n.batchReportLength = null !== (e = t.maxBatchReportLength) && void 0 !== e ? e : 10, n.batchReportWait = null !== (t = t.batchReportWait) && void 0 !== t ? t : 1e3, n.batchReportTimeout = null, n
    }

    var sr = (bt = ".") + "/sentry.3.6.33.cn.js",
        cr = bt + "/behavior.3.6.33.cn.js", lr = "i.snssdk.com";

    function ur(t) {
        return !!t && Math.random() < Number(t)
    }

    function pr(t) {
        var e;
        if (null !== (e = window.__SLARDAR__) && void 0 !== e && e.plugins && Xe(null === (n = window.__SLARDAR__) || void 0 === n ? void 0 : n.plugins)) {
            var n = null === (n = window.__SLARDAR__) || void 0 === n ? void 0 : n.plugins, r = void 0;
            return n.forEach((function (e) {
                e.version === t.version && e[t.name] && (r = e[t.name])
            })), r
        }
    }

    var dr, hr = (e(fr, dr = ir), fr.prototype.setEnable = function (t) {
        this.enable || (this.enable = t, this.enable && this.updateStatus())
    }, fr.prototype.getReportURL = function () {
        return this.reportURL || this.getBatchReportURL()
    }, Object.defineProperty(fr.prototype, "contextAgent", {
        get: function () {
            var t = this, e = {
                set: function (n, r) {
                    return t.options && (t.options.commonParams.context || (t.options.commonParams.context = {}), t.options.commonParams.context[n] = r), e
                }, delete: function (n) {
                    var r;
                    return null !== (r = t.options) && void 0 !== r && r.commonParams.context && "string" != typeof t.options.commonParams.context && Ge(t.options.commonParams.context, n) && delete t.options.commonParams.context[n], e
                }, clear: function () {
                    return t.options && (t.options.commonParams.context = {}), e
                }, get: function (e) {
                    var n;
                    return null === (n = null === (n = null === (n = t.options) || void 0 === n ? void 0 : n.commonParams) || void 0 === n ? void 0 : n.context) || void 0 === n ? void 0 : n[e]
                }, toObject: function () {
                    var e;
                    return "string" == typeof (null === (e = t.options) || void 0 === e ? void 0 : e.commonParams.context) ? {} : n({}, null === (e = null === (e = t.options) || void 0 === e ? void 0 : e.commonParams) || void 0 === e ? void 0 : e.context)
                }
            };
            return e
        }, enumerable: !1, configurable: !0
    }), fr.prototype._shouldSend = function (t) {
        if (!(null !== (e = this.options) && void 0 !== e && e.monitors && t && We(t) && null !== (n = t.event) && void 0 !== n && n.ev_type)) return !1;
        var e = this.options.monitors;
        if ("AjaxMonitor" === t.name || "FetchMonitor" === t.name) {
            var n = dn(xn || []);
            if (n && n.test(t.event.ax_url)) return !1
        }
        return !!e.BaseMonitor.webIDHit || !!e.BaseMonitor.sampleHit && ("PathMonitor" === t.name ? function (t) {
            var e = t.sendParams, n = t.pageViewMonitor;
            return 1 === (t = t.baseMonitor).appTypeSetting.type && e.event.type === t.appTypeSetting.SPA && (!n || (!!n.webIDHit || !!n.sampleHit))
        }({
            sendParams: t,
            pageViewMonitor: e.PageViewMonitor,
            baseMonitor: e.BaseMonitor
        }) : "PageViewMonitor" === t.name ? function (t) {
            return !(t = t.pageViewMonitor) || (!!t.webIDHit || !!t.sampleHit)
        }({pageViewMonitor: e.PageViewMonitor}) : "AjaxMonitor" === t.name || "FetchMonitor" === t.name ? function (t) {
            var e = t.sendParams, n = t.ajaxMonitor;
            if (!n) return !0;
            if (n.webIDHit) return !0;
            if (!n.sampleHit) return !1;
            if (Xe(t = n.whitelistUrls) && 0 < t.length) {
                var r = dn(n.whitelistUrls || []);
                return !(!r || !r.test(e.event.ax_url))
            }
            if ((r = dn(n.ignore || [])) && r.test(e.event.ax_url)) return !1;
            if ((r = n.statusCodeSample) && Ge(r, e.event.ax_status)) return ur(r[e.event.ax_status]);
            if (n = n.requestUrlSample) {
                var o = !1, i = !1;
                if (n.forEach((function (t) {
                    var n;
                    o || null != (n = dn([t.url])) && n.test(e.event.ax_url) && (o = !0, i = ur(t.sampleRate))
                })), o) return i
            }
            return !0
        }({sendParams: t, ajaxMonitor: e.AjaxMonitor}) : "PerformanceMonitor" === t.name ? function (t) {
            return !(t = t.performanceMonitor) || (!!t.webIDHit || !!t.sampleHit)
        }({performanceMonitor: e.PerformanceMonitor}) : "EmitMonitor" === t.name ? function (t) {
            var e = t.sendParams;
            return !(t = t.flexibleMonitor) || (!!t.webIDHit || !!t.sampleHit && (!((t = t.eventNameHit) && (e = e.event.flexible_data_list[0].event_name, t && Ge(t, e))) || ur(t[e])))
        }({sendParams: t, flexibleMonitor: e.FlexibleMonitor}) : "StaticErrorMonitor" === t.name ? function (t) {
            var e = t.sendParams;
            return !(t = t.staticErrorMonitor) || (!!t.webIDHit || !!t.sampleHit && (!(t = dn(t.ignore || [])) || !t.test(e.event.st_src)))
        }({sendParams: t, staticErrorMonitor: e.StaticErrorMonitor}) : "HijackMonitor" === t.name ? function (t) {
            return !(t = t.hijackMonitor) || (!!t.webIDHit || !!t.sampleHit)
        }({hijackMonitor: e.HijackMonitor}) : "JSErrorMonitor" !== t.name || function (t) {
            return !(t = t.jsErrorMonitor) || (!!t.webIDHit || !!t.sampleHit)
        }({jsErrorMonitor: e.JSErrorMonitor}))
    }, fr.prototype._modifyEvent = function (t) {
        if (null === (e = this.options) || void 0 === e || !e.monitors || !t || !We(t)) return {};
        var e = this.options.monitors;
        return "PerformanceMonitor" === t.name ? function (t) {
            var e, n, r = t.sendParams;
            return (t = t.performanceMonitor) ? nn() && We(window.performance) && We(window.performance.timing) ? (e = r.event.isAsync ? r.event.load > t.spaSlowSessionTime : (e = window.performance.timing).loadEventEnd - e.navigationStart > t.slowSessionTime, r.event.resources && Xe(r.event.resources) && (n = dn(t.geckoUrls || []), r.event.resources.forEach((function (t, e) {
                var o, i;
                null !== (o = r.event.resources) && void 0 !== o && o[e] && We(null === (o = r.event.resources) || void 0 === o ? void 0 : o[e]) && Qe(null === (i = r.event.resources) || void 0 === i ? void 0 : i[e].toJSON) && (r.event.resources[e] = null === (i = r.event.resources) || void 0 === i ? void 0 : i[e].toJSON(), r.event.resources[e].is_gecko = n && n.test(t.name || "") ? "1" : "0")
            }))), t.webIDHitStaticResource || t.sampleHitStaticResource ? r.event.upload_reason = "sample" : e ? r.event.upload_reason = "slow_session" : (delete r.event.resources, r.event.has_resource = 0), r) : {} : r
        }({sendParams: t, performanceMonitor: e.PerformanceMonitor}) : "PathMonitor" === t.name ? function (t) {
            var e = t.sendParams, n = t.baseMonitor;
            t = t.effect;
            return 1 === n.appTypeSetting.type && n.appTypeSetting.SPA === e.event.type && (t({commonParams: {pid: e.event.path}}), delete e.event.type, delete e.event.path), {}
        }({sendParams: t, baseMonitor: e.BaseMonitor, effect: this.updateConfig}) : t
    }, fr.prototype.buildParams = function (t) {
        var e = t.event || {}, o = t.overrides || {}, i = o.context, a = r(o, ["context"]);
        t = n({}, null !== (o = null === (t = this.options) || void 0 === t ? void 0 : t.commonParams) && void 0 !== o ? o : {});
        return We(i) && (i = n(n({}, null !== (o = We(t.context) && t.context) && void 0 !== o ? o : {}), i || {}), t.context = i), t = n(n(n({}, e), t || {}), a), t = this.normalizeEvent(t), null !== (a = this.options) && void 0 !== a && a.custom && Qe(this.options.custom.beforeSend) ? this.options.custom.beforeSend(t || {}) : t
    }, fr.prototype._request = function (t) {
        var e, n;
        t && t.event && (e = t.type, n = t.event, (t = window.automation_test_info) && (n.automation_test_info = t), "beacon" === (null === (t = this.options) || void 0 === t ? void 0 : t.commonParams.report_type) && navigator && navigator.sendBeacon ? (t = JSON.stringify(n), navigator.sendBeacon(this.getReportURL(), t)) : this.transport && ("get" !== e ? "post" === e && this.transport.postEvent(n) : this.transport.getEvent(n)))
    }, fr.prototype.chechIsReady = function () {
        return 2 === this.clientStatus
    }, fr.prototype.updateStatus = function () {
        var t = this;
        switch (this.clientStatus) {
            case 0:
                this.clientStatus = 1;
                break;
            case 1:
                if (this.clientStatus = 2, !this.preQueue.length) return;
                this.preQueue.forEach((function (e) {
                    dr.prototype.sendEvent.call(t, e)
                })), this.preQueue = []
        }
    }, fr.prototype.getBatchReportURL = function () {
        var t;
        // return "https://" + (null === (t = this.options) || void 0 === t ? void 0 : t.commonParams.report_domain) + "/log/sentry/v2/api/slardar/batch/"
        return ""
    }, fr.prototype.normalizeOptions = function (t) {
        var e;
        return Ye({}, null !== (e = this.options) && void 0 !== e ? e : {}, t)
    }, fr.prototype.normalizeEvent = function (t) {
        var e = n(n({}, t), {url: window.location.href, client_time: Date.now()});
        return We(t.context) && (e.context = JSON.stringify(t.context)), e || {}
    }, fr);

    function fr(t) {
        var e = dr.call(this, null != t ? t : {reportURL: ""}) || this;
        return e.setTransport = function () {
            e.transport || (e.transport = new vn({reportURL: e.getReportURL()}), e.updateStatus())
        }, e.setOnOptionsUpdate = function (t) {
            e.onOptionsUpdate = t
        }, e.uploadQueue = function () {
            e._uploadQueue()
        }, e.updateConfig = function (t) {
            var n,
                r = null === (r = null === (r = e.options) || void 0 === r ? void 0 : r.commonParams) || void 0 === r ? void 0 : r.pid;
            e.options = e.normalizeOptions(t), r && null !== (n = null == t ? void 0 : t.commonParams) && void 0 !== n && n.pid && t.commonParams.pid !== r && e.sendPageview(), e.onOptionsUpdate && e.onOptionsUpdate(e.options)
        }, e.sendPageview = function () {
            Qe(e.sendEvent) && e.sendEvent({name: "PageViewMonitor", type: "get", event: {ev_type: "pageview"}})
        }, e.sendEvent = function (t) {
            e.chechIsReady() ? dr.prototype.sendEvent.call(e, t) : e.preQueue.push(t)
        }, e.buildSendParams = function (t) {
            return e.buildParams(t)
        }, e.reportURL = null !== (t = null == t ? void 0 : t.reportURL) && void 0 !== t ? t : "", e.preQueue = [], e.clientStatus = 0, e.enable = !1, e
    }

    var mr = function () {
        var t = "";
        return null !== document && void 0 !== document && document.cookie ? function (t, e) {
            var n, r;
            if (!t) return "";
            t = t.split(";");
            var i = {};
            try {
                for (var a = o(t), s = a.next(); !s.done; s = a.next()) {
                    var c = s.value.split("="), l = Ve(c[0]) && c[0].trim();
                    l && Ve(c[1]) && (i[l] = c[1].trim())
                }
            } catch (t) {
                n = {error: t}
            } finally {
                try {
                    s && !s.done && (r = a.return) && r.call(a)
                } finally {
                    if (n) throw n.error
                }
            }
            return i[e] || ""
        }(document.cookie, Rn) : t
    };

    function vr(t) {
        return Ye({}, {
            commonParams: {
                version: "",
                hostname: window.location.hostname,
                protocol: window.location.protocol.slice(0, -1),
                url: window.location.href,
                slardar_session_id: Cn,
                sample_rate: 1,
                pid: location.pathname,
                report_domain: lr,
                screen_resolution: hn(),
                network_type: fn(),
                bid: "",
                context: {},
                slardar_web_id: mr() || jn,
                report_type: "xhr",
                performanceAuto: !0,
                reportURLSingle: "",
                region: "cn",
                env: "production",
                refreshPrecollectedContext: !1,
                maxBatchReportLength: 10,
                batchReportWait: 1e3
            },
            flags: {
                hookPath: !0,
                hookXHR: !0,
                hookFetch: !0,
                enableSizeStats: !0,
                enableFMP: !0,
                enablePerformance: !0,
                enableResourcePerformance: !0,
                enableStaticError: !0,
                enableCatchJSError: !0,
                enableCatchGlobalJSError: !0,
                enableCatchJSErrorV2: !1,
                enableCrash: !1,
                enableMemoryRecord: !1,
                enableFPSJankTimesMonitor: !1,
                enableBreadcrumb: !0,
                catchUnhandledRejection: !0,
                catchUnhandledRejectionV2: !0,
                hookConsole: !1,
                hookDom: !0
            },
            monitors: {
                BaseMonitor: {appTypeSetting: {type: 0, SPA: "", renderType: 0}, webIDHit: !1, sampleHit: mn(1)},
                PageViewMonitor: {webIDHit: !1, sampleHit: mn(1)},
                JSErrorMonitor: {
                    webIDHit: !1,
                    sampleHit: mn(1),
                    webIDHitBehavior: !1,
                    sampleHitBehavior: !1,
                    ignoreErrors: [],
                    whitelistUrls: [],
                    blacklistUrls: [],
                    download_link: ""
                },
                AjaxMonitor: {
                    webIDHit: !1,
                    sampleHit: mn(1),
                    sampleHitTrace: !1,
                    ignore: xn,
                    abort: !1,
                    whitelistUrls: [],
                    statusCodeSample: {},
                    requestUrlSample: [],
                    errorRequestCollect: !1
                },
                PerformanceMonitor: {
                    webIDHit: !1,
                    sampleHit: mn(1),
                    webIDHitStaticResource: !1,
                    sampleHitStaticResource: mn(.1),
                    slowSessionTime: 8e3,
                    spaSlowSessionTime: 4e3,
                    geckoUrls: [],
                    interval: 0,
                    checkPoint: []
                },
                FlexibleMonitor: {webIDHit: !1, sampleHit: mn(1), eventNameHit: {}, eventNameSampleHit: mn(1)},
                StaticErrorMonitor: {webIDHit: !1, sampleHit: mn(1), ignore: xn},
                HijackMonitor: {webIDHit: !1, sampleHit: mn(.1)},
                FMPMonitor: {}
            },
            plugins: {
                render: {enable: 0},
                spa: {enable: 0, type: ""},
                behavior: {enable: 0, slardar_web_ids: [], sample_rate: 0, download_link: cr},
                sentry: {
                    enable: 0,
                    behavior_enable: 0,
                    behavior_sample_rate: 0,
                    behavior_slardar_web_ids: [],
                    download_link: sr
                }
            },
            custom: {
                beforeSend: function (t) {
                    return t
                }
            },
            heatmap: {openList: []}
        }, t)
    }

    function gr(t, e) {
        var n = !1;
        return t && t.forEach((function (t) {
            n || !t.condition.is_default && "slardar_web_id" === t.condition.field && en(t.condition.values, e.web_id) && (n = !0)
        })), n
    }

    function yr(t) {
        var e = !1;
        return !t || (t.forEach((function (t) {
            t.condition.field && e || (e = ur(Number(t.value)))
        })), e)
    }

    function br(t) {
        var e = [];
        if (t) try {
            e = JSON.parse(t)
        } catch (t) {
            e = []
        }
        return e
    }

    function wr(t) {
        var e = [];
        return t.forEach((function (t) {
            t.value && (t = br(t.value), e.push.apply(e, a([], i(t))))
        })), e
    }

    function _r(t, e) {
        var n = {}, r = [];
        return t.forEach((function (t) {
            t.condition.field === e && t.condition.values && t.condition.values.forEach((function (e) {
                Ge(n, e) || (n[e] = Number(t.value) || 0, r.push({url: e, sampleRate: n[e]}))
            }))
        })), {flatten: n, list: r}
    }

    function Sr(t) {
        var e, n = Ye({}, null !== (e = (t = void 0 === t ? {} : t).commonParams) && void 0 !== e ? e : {});
        if (!We(t)) return n;
        var r = ["bid", "context", "pid", "slardar_web_id", "slardar_session_id", "performanceAuto", "report_type", "region", "env", "refreshPrecollectedContext", "maxBatchReportLength", "batchReportWait"];
        return $e(t, (function (t, e) {
            en(r, t) && ("report_domain" === t && "mon-va.byteoversea.com" === e ? n.region = "maliva" : n[t] = e)
        })), $e({
            cookieid: "slardar_web_id",
            sampleRate: "sample_rate",
            reportDomain: "report_domain",
            domain: "report_domain",
            sample_rate: "sample_rate",
            slardar_web_id: "slardar_web_id"
        }, (function (e, r) {
            Ge(t, e) && (n[r] = t[e])
        })), n.slardar_web_id && (n.slardar_web_id = n.slardar_web_id.toString()), n
    }

    function Er(t, e) {
        var n, r, i, a = {
            PageViewMonitor: {},
            JSErrorMonitor: {},
            AjaxMonitor: {},
            PerformanceMonitor: {},
            FlexibleMonitor: {},
            StaticErrorMonitor: {},
            HijackMonitor: {},
            BaseMonitor: {}
        }, s = null !== (n = t.setting) && void 0 !== n ? n : {};
        return null !== (n = null === (n = s.page_view) || void 0 === n ? void 0 : n.enable_rate) && void 0 !== n && n.length && (a.PageViewMonitor.webIDHit = gr(s.page_view.enable_rate, e), a.PageViewMonitor.sampleHit = yr(s.page_view.enable_rate)), s.jserr && (null !== (r = s.jserr.enable_rate) && void 0 !== r && r.length && (a.JSErrorMonitor.webIDHit = gr(s.jserr.enable_rate, e), a.JSErrorMonitor.sampleHit = yr(s.jserr.enable_rate)), s.jserr.ignore_errors && (a.JSErrorMonitor.ignoreErrors = wr(s.jserr.ignore_errors)), s.jserr.blacklist_urls && (a.JSErrorMonitor.blacklistUrls = wr(s.jserr.blacklist_urls)), s.jserr.whitelist_urls && (a.JSErrorMonitor.whitelistUrls = wr(s.jserr.whitelist_urls))), s.ajax && (s.ajax.enable_rate && (a.AjaxMonitor.webIDHit = gr(s.ajax.enable_rate, e), a.AjaxMonitor.sampleHit = yr(s.ajax.enable_rate)), s.ajax.request_sample_rate && (a.AjaxMonitor.statusCodeSample = _r(s.ajax.request_sample_rate, "status_code").flatten, a.AjaxMonitor.requestUrlSample = _r(s.ajax.request_sample_rate, "request_url").list), s.ajax.trace_rate && (a.AjaxMonitor.sampleHitTrace = yr(s.ajax.trace_rate)), s.ajax.enable_request_param_collect_on_error && (a.AjaxMonitor.errorRequestCollect = function (t, e) {
            var n, r;
            void 0 === e && (e = !1);
            try {
                for (var i = o(t), a = i.next(); !a.done; a = i.next()) {
                    var s = a.value;
                    if (s.value) return "true" === s.value
                }
            } catch (t) {
                n = {error: t}
            } finally {
                try {
                    a && !a.done && (r = i.return) && r.call(i)
                } finally {
                    if (n) throw n.error
                }
            }
            return e
        }(s.ajax.enable_request_param_collect_on_error))), null !== (r = s.static_resource) && void 0 !== r && r.enable_rate && (a.PerformanceMonitor.webIDHitStaticResource = gr(s.static_resource.enable_rate, e), a.PerformanceMonitor.sampleHitStaticResource = yr(s.static_resource.enable_rate)), null !== (r = s.performance) && void 0 !== r && r.enable_rate && (a.PerformanceMonitor.webIDHit = gr(s.performance.enable_rate, e), a.PerformanceMonitor.sampleHit = yr(s.performance.enable_rate)), s.flexible && (s.flexible.enable_rate && (a.FlexibleMonitor.webIDHit = gr(s.flexible.enable_rate, e), a.FlexibleMonitor.sampleHit = yr(s.flexible.enable_rate)), s.flexible.event_name_sample_rate && (a.FlexibleMonitor.eventNameHit = _r(s.flexible.event_name_sample_rate, "event_name").flatten, a.FlexibleMonitor.eventNameSampleHit = yr(s.flexible.event_name_sample_rate))), null !== (r = s.static_err) && void 0 !== r && r.enable_rate && (a.StaticErrorMonitor.webIDHit = gr(s.static_err.enable_rate, e), a.StaticErrorMonitor.sampleHit = yr(s.static_err.enable_rate)), !s.general || (s = s.general.app_type_setting) && (s = {
            type: Number(s.app_type[0].value),
            SPA: s.app_type_spa[0].value,
            renderType: Mr({
                renderRules: (s = s.app_render_type, i = [], s.forEach((function (t) {
                    "pid" === t.condition.field.toLowerCase() && "like" === t.condition.op.toLowerCase() && t.value ? i.push({
                        pids: br(t.value),
                        value: parseInt(t.value, 10),
                        isDefault: !1
                    }) : t.condition.is_default && i.push({pids: [], value: parseInt(t.value, 10), isDefault: !0})
                })), i), pid: e.pid
            })
        }, a.BaseMonitor.appTypeSetting = s), Xe(t = t.whitelist) && t.forEach((function (t) {
            e.web_id === t && (a.BaseMonitor.webIDHit = !0, a.AjaxMonitor.sampleHitTrace = !0)
        })), a
    }

    function Mr(t) {
        var e = t.renderRules, n = t.pid;
        if (!e || !n) return 0;
        for (var r = 0; r < e.length; r++) {
            var o = e[r];
            if (Xe(o.pids) && 0 < o.pids.length) {
                var i = dn(o.pids);
                if (i && i.test(n)) return o.value
            }
            if (o.isDefault && r === e.length - 1) return o.value
        }
        return 0
    }

    var Pr = (xr.prototype.handleUserConfig = function (t) {
        this.userConfig = function () {
            for (var t = [], e = 0; e < arguments.length; e++) t[e] = arguments[e];
            for (var r = {}, o = 0; o < t.length;) r = function (t, e) {
                var r, o = n({}, t);
                for (r in e) Object.prototype.hasOwnProperty.call(e, r) && void 0 !== e[r] && (We(e[r]) && Ze(e[r]) ? o[r] = tn(We(t[r]) ? t[r] : {}, e[r]) : Xe(e[r]) ? o[r] = e[r].slice() : o[r] = e[r]);
                return o
            }(r, t[o]), o++;
            return r
        }(this.userConfig, t), null !== (t = this.userConfig) && void 0 !== t && t.onlyUseLocalSetting && (this.serverSetting = {}), this.mergeSettings()
    }, xr.prototype.setBaseParams = function () {
        this.baseParams.bid = this.shared.config.commonParams.bid || "", this.baseParams.pid = this.shared.config.commonParams.pid || ""
    }, xr.prototype.init = function (t) {
        var e = this;
        this.handleUserConfig(t);
        var n = this.finalConfig;
        this.inited || (this.inited = !0, this.client = new hr({
            // reportURL: "https://" + (null != (t = n.commonParams.report_domain) ? t : lr) + "/log/sentry/v2/api/slardar/batch/",
            // reportURL: "/error",
            maxBatchReportLength: n.commonParams.maxBatchReportLength,
            batchReportWait: n.commonParams.batchReportWait
        }), this.client.setOnOptionsUpdate((function (t) {
            t && t.commonParams.pid !== e.finalConfig.commonParams.pid && e.handleUserConfig({pid: t.commonParams.pid})
        })), n.commonParams.refreshPrecollectedContext && this.refreshPrecollectedContext(), t = this.setMonitors(), this.setupMonitors(t), this.shared.instance && Xe(this.shared.instance) && this.shared.instance.push(n.commonParams.bid), this.getServerSetting(n.commonParams.bid), this.onClose())
    }, xr.prototype.config = function (t) {
        var e, n;
        this.configed = !0, this.inited ? (this.handleUserConfig(t), n = this.finalConfig, null !== (e = this.client) && void 0 !== e && e.updateConfig(n)) : this.init(t), this.tryUnlockClient()
    }, xr.prototype.getServerSetting = function (t) {
        var e, n = this;
        this.serverSetting ? this.handleServerSetting() : (t = "https://" + (null != (e = this.finalConfig.commonParams.report_domain) ? e : lr) + "/slardar/sdk_setting?bid=" + t, vn.get(t, {
            success: function (t) {
                try {
                    n.serverSetting = t.data || {}, n.handleServerSetting()
                } catch (t) {
                    n.userConfig.sampleRate = .001, n.handleServerSetting()
                }
            }, fail: function () {
                n.userConfig.sampleRate = .001, n.handleServerSetting()
            }, withCredentials: !0
        }))
    }, xr.prototype.setMonitors = function () {
        var t, e = this, n = [], r = this.finalConfig.flags, o = this.finalConfig.commonParams;
        return r.hookPath && n.push(new Zn), r.hookXHR && n.push(new er({
            commonParams: {
                web_id: o.slardar_web_id,
                bid: o.bid
            }, ajaxMonitorOptions: this.finalConfig.monitors.AjaxMonitor
        })), r.hookFetch && n.push(new nr({
            commonParams: {web_id: o.slardar_web_id, bid: o.bid},
            ajaxMonitorOptions: this.finalConfig.monitors.AjaxMonitor
        })), r.enableFMP && (t = null === (t = function (t, e, n) {
            var r;
            return void 0 === t && (t = function () {
                if (We(document)) return document
            }()), void 0 === e && (e = function () {
                if (Hn() && Qe(window.MutationObserver)) return window.MutationObserver
            }()), void 0 === n && (n = null === (r = function () {
                var t = Ln();
                if (t && We(t.timing)) return t.timing
            }()) || void 0 === r ? void 0 : r.navigationStart), function (r, o) {
                function a() {
                    return d.push({time: Date.now() - p, score: Yn(t && t.body, 1, !1, h)})
                }

                var s, c, l, u, p = Date.now(), d = [], h = $n.concat(r.ignoreTags || []),
                    f = i((g = !0, m = window.requestAnimationFrame, r = window.cancelAnimationFrame, c = !Qe(m) || g && document && document.hidden ? function (t) {
                        return t(0), 0
                    } : m, l = Qe(r) ? r : cn, [function (t) {
                        s && l(s), s = c(t)
                    }, c, l]), 1)[0], m = (r = i((g = function () {
                        return f(a)
                    }, u = (m = e) && new m(g), [function (t, e) {
                        u && t && u.observe(t, e)
                    }, function () {
                        return u && u.disconnect()
                    }]), 2))[0], v = r[1], g = function (t) {
                        void 0 === t && (t = 0), v();
                        var e, n;
                        t = {
                            name: "FMPMonitor",
                            type: "post",
                            event: {
                                ev_type: "fmp",
                                fmp: (n = (n = (e = i(void 0 === (n = d) ? [] : n))[0], (e = e.slice(1)) && e.reduce((function (t, e) {
                                    var n = (r = i(t, 2))[0], r = (t = r[1], e.score - n.score);
                                    return [e, e.time >= n.time && t.rate < r ? {time: e.time, rate: r} : t]
                                }), [n, {time: null == n ? void 0 : n.time, rate: 0}])[1].time || 0)) ? n + t : 0
                            }
                        };
                        return o && o(t), t
                    };
                r = p - (n || 0);
                return m(t, {subtree: !0, childList: !0}), [v, g, g.bind(null, r)]
            }
        }()) || void 0 === t ? void 0 : t(this.finalConfig.monitors.FMPMonitor), this.shared.FMPMonitor = t), null !== (t = this.shared) && void 0 !== t && t.monitors && n.forEach((function (t) {
            e.shared.monitors[t.name] = t
        })), n
    }, xr.prototype.setupMonitors = function (t) {
        var e = this;
        this.client && this.client.sendEvent && t.forEach((function (t) {
            t.setup(null === (t = e.client) || void 0 === t ? void 0 : t.sendEvent)
        }))
    }, xr.prototype.shouldSetClientEnable = function () {
        return "production" === this.finalConfig.commonParams.env
    }, xr.prototype.tryUnlockClient = function () {
        var t;
        null !== (t = this.client) && void 0 !== t && t.setEnable(this.shouldSetClientEnable() && this.configed && !!this.serverSetting)
    }, xr.prototype.handleServerSetting = function () {
        var t;
        this.mergeSettings();
        var e = this.finalConfig;
        this.client.updateConfig(e), this.client.setTransport(), this.tryUnlockClient(), this.client.sendPageview(), this.shared.transport = this.client.transport, this.shared.sendEvent = this.client.sendEvent.bind(this.client), this.setCookie(e.commonParams.slardar_web_id), null !== (t = this.shared.monitors.FetchMonitor) && void 0 !== t && t.updateConfig({
            commonParams: {
                web_id: e.commonParams.slardar_web_id,
                bid: e.commonParams.bid
            }, ajaxMonitorOptions: e.monitors.AjaxMonitor
        }), null !== (t = this.shared.monitors.AjaxMonitor) && void 0 !== t && t.updateConfig({
            commonParams: {
                web_id: e.commonParams.slardar_web_id,
                bid: e.commonParams.bid
            }, ajaxMonitorOptions: e.monitors.AjaxMonitor
        }), this.loadMonitors(), e.flags.enableCatchJSError && this.loadSentry(), e.heatmap.openList.length && this.loadHeatmap(), -1 < location.search.indexOf("slardar_heatmap_draw") && this.loadHeatmapDraw()
    }, xr.prototype.loadSentry = function () {
        var t = this;
        On(sr, (function () {
            var e = pr({name: "SetSentryMonitors", version: t.version});
            e && ((e = new e({
                catchUnhandledRejection: t.finalConfig.flags.catchUnhandledRejection,
                hookConsole: t.finalConfig.flags.hookConsole,
                hookDom: t.finalConfig.flags.hookDom,
                memoryRecordMonitor: t.shared.monitors.MemoryRecordMonitor,
                collect: t.shared.collect,
                sendEvent: t.client.sendEvent,
                jsErrorOptions: t.shared.config.monitors.JSErrorMonitor
            })).init(), t.shared.monitors = n(n({}, t.shared.monitors), e.getInstalledMonitors()))
        }))
    }, xr.prototype.loadMonitors = function () {
        var t = this;
        On("./monitors.3.6.33.cn.js", (function () {
            var e, r = pr({name: "SetMonitors", version: t.version});
            if (r) try {
                var o = new r({
                    config: t.finalConfig,
                    fmpMonitor: t.shared.FMPMonitor,
                    report: function () {
                        return t.instance("report")
                    },
                    sendEvent: null === (e = t.client) || void 0 === e ? void 0 : e.sendEvent,
                    captureException: function (e) {
                        return t.instance("Sentry", (function (t) {
                            t.captureException(e)
                        }))
                    },
                    collect: t.shared.collect
                });
                o.init(), t.shared.monitors = n(n({}, t.shared.monitors), o.getInstalledMonitors())
            } catch (e) {
                t.instance("Sentry", (function (t) {
                    t.captureException(e);
                    try {
                        t.withScope((function (e) {
                            e.setTag("from", "slardar-sdk"), t.captureMessage("SLARDAR_ERROR: " + typeof r), t.captureMessage("SLARDAR_ERROR: " + r.toString())
                        }))
                    } catch (e) {
                        t.withScope((function (n) {
                            n.setTag("from", "slardar-sdk"), t.captureException(e)
                        }))
                    }
                }))
            }
        }))
    }, xr.prototype.loadHeatmap = function () {
        var t = this;
        On("/heatmap.3.6.33.cn.js", (function () {
            var e = pr({name: "SetHeatmap", version: t.version});
            e && ((e = new e({
                config: t.finalConfig,
                report: function () {
                    return t.instance("report")
                },
                sendEvent: t.client.sendEvent,
                collect: t.shared.collect,
                pathMonitor: t.shared.monitors.PathMonitor,
                buildSendParams: t.client.buildSendParams,
                reportURL: t.client.getReportURL()
            })).init(), t.shared.monitors = n(n({}, t.shared.monitors), e.getInstalledMonitors()))
        }))
    }, xr.prototype.loadHeatmapDraw = function () {
        var t = this;
        On("/heatmap-draw.3.6.33.cn.js", (function () {
            var e = pr({name: "SetHeatmapDraw", version: t.version});
            e && new e({config: t.finalConfig, pathMonitor: t.shared.monitors.PathMonitor}).init()
        }))
    }, xr.prototype.setCookie = function (t) {
        t && (document.cookie = Rn + "=" + t + ";max-age=7776000;domain=" + location.hostname + ";path=/")
    }, xr.prototype.normalizeSetting = function (t) {
        return {
            commonParams: this.getClientCommonParams(t),
            flags: this.getClientFlags(t),
            plugins: this.getClientPluginsSetting(t),
            monitors: this.getClientMonitorsSetting(t),
            custom: this.getClientCustom(t),
            heatmap: this.getClientHeatmap(t)
        }
    }, xr.prototype.normalizeServerSetting = function (t) {
        return Ge(t, "setting") ? {
            commonParams: Sr(t),
            monitors: Er(t, this.getNormoalizeInfo()),
            heatmap: t.heatmap
        } : this.normalizeSetting({
            cookieid: t.cookieid,
            reportDomain: t.reportDomain,
            bid: t.bid,
            plugins: t.plugins,
            heatmap: null !== (t = t.heatmap) && void 0 !== t ? t : {openList: []}
        })
    }, xr.prototype.getClientHeatmap = function (t) {
        return null !== (t = t.heatmap) && void 0 !== t ? t : {}
    }, xr.prototype.mergeSettings = function () {
        var t = this.serverSetting ? this.normalizeServerSetting(this.serverSetting) : {},
            e = this.normalizeSetting(this.userConfig);
        (t = Ye(this.defaultClientConfig, t, e)).commonParams.reportURLSingle = "https://" + (null != (e = t.commonParams.report_domain) ? e : lr) + "/log/sentry/v2/api/slardar/main/", t.commonParams.pid = this.getPid(t), this.shared.config = t, this.setBaseParams(), this.finalConfig = t
    }, xr.prototype.getPid = function (t) {
        return t.commonParams.pid ? t.commonParams.pid : 1 === (t = t.monitors.BaseMonitor).appTypeSetting.type && "hash" === t.appTypeSetting.SPA ? location.hash.slice(1) || "/" : location.pathname
    }, xr.prototype.getClientCommonParams = Sr, xr.prototype.getClientFlags = function (t) {
        var e, n = Ye({}, null !== (e = (t = void 0 === t ? {} : t).flags) && void 0 !== e ? e : {});
        if (!We(t)) return n;
        var r = ["hookPath", "hookXHR", "hookFetch", "enableSizeStats", "enableFMP", "enablePerformance", "enableStaticError", "enableCatchJSError", "enableCatchJSErrorV2", "enableCatchGlobalJSError", "enableResourcePerformance", "enableCrash", "enableMemoryRecord", "enableFPSJankTimesMonitor", "enableBreadcrumb", "catchUnhandledRejection", "catchUnhandledRejectionV2", "hookConsole", "hookDom"];
        return $e(t, (function (t, e) {
            en(r, t) && (n[t] = e)
        })), n
    }, xr.prototype.getClientCustom = function (t) {
        var e = {};
        return t.beforeSend && Qe(t.beforeSend) && (e.beforeSend = t.beforeSend), e
    }, xr.prototype.getClientMonitorsSetting = function (t) {
        return function (t) {
            var e, r = t.setting, o = t.info, i = Ye({
                PageViewMonitor: {},
                JSErrorMonitor: {},
                AjaxMonitor: {},
                PerformanceMonitor: {},
                FlexibleMonitor: {},
                StaticErrorMonitor: {},
                HijackMonitor: {},
                BaseMonitor: {appTypeSetting: {}},
                FMPMonitor: {}
            }, r.monitors);
            return r.ajaxWhitelistUrls && Xe(r.ajaxWhitelistUrls) && (i.AjaxMonitor.whitelistUrls = (i.AjaxMonitor.whitelistUrls || []).concat(r.ajaxWhitelistUrls)), r.ignoreAjax && Xe(r.ignoreAjax) && (i.AjaxMonitor.ignore = (i.AjaxMonitor.ignore || []).concat(r.ignoreAjax)), Ge(r, "geckoUrls") && (i.PerformanceMonitor.geckoUrls = r.geckoUrls), r.ignoreStatic && Xe(r.ignoreStatic) && (i.StaticErrorMonitor.ignore = (i.StaticErrorMonitor.ignore || []).concat(r.ignoreStatic)), null !== (e = r.plugins) && void 0 !== e && e.sentry && (i.JSErrorMonitor = n(n({}, i.JSErrorMonitor || {}), r.plugins.sentry)), r.errorRelease && (i.JSErrorMonitor.release = r.errorRelease), r.ignoreErrors && Xe(r.ignoreErrors) && (i.JSErrorMonitor.ignoreErrors = r.ignoreErrors), r.errorBlacklistUrls && Xe(r.errorBlacklistUrls) && (i.JSErrorMonitor.blacklistUrls = r.errorBlacklistUrls), r.errorWhitelistUrls && Xe(r.errorWhitelistUrls) && (i.JSErrorMonitor.whitelistUrls = r.errorWhitelistUrls), r.fmpIgnoreTags && Xe(r.fmpIgnoreTags) && (i.FMPMonitor.ignoreTags = r.fmpIgnoreTags), Ge(r, "sampleRate") && (i.BaseMonitor.sampleHit = ur(r.sampleRate)), (t = r.plugins) && We(t) && (Ge(t, "spa") && ((e = t.spa) && Ge(e, "enable") && (i.BaseMonitor.appTypeSetting.type = Number(e.enable)), e && Ge(e, "type") && (i.BaseMonitor.appTypeSetting.SPA = e.type)), !t.render || (r = null === (r = r.plugins) || void 0 === r ? void 0 : r.render) && We(r) && Ge(r, "enable") && (r = [{
                pids: [],
                value: r.enable,
                isDefault: !0
            }], i.BaseMonitor.appTypeSetting.renderType = Mr({renderRules: r, pid: o.pid}))), i
        }({setting: t, info: this.getNormoalizeInfo()})
    }, xr.prototype.getClientPluginsSetting = function (t) {
        return Ye({
            sentry: {},
            behavior: {},
            spa: {},
            render: {}
        }, null !== (t = (t = void 0 === (t = t) ? {} : t).plugins) && void 0 !== t ? t : {})
    }, xr.prototype.getNormoalizeInfo = function () {
        return {web_id: this.finalConfig.commonParams.slardar_web_id, pid: this.finalConfig.commonParams.pid}
    }, xr.prototype.saveInstance = function () {
        window.__SLARDAR__ || (window.__SLARDAR__ = {}), window.__SLARDAR__ && !Xe(window.__SLARDAR__.instances) && (window.__SLARDAR__.instances = []), window.__SLARDAR__ && Xe(window.__SLARDAR__.instances) && window.__SLARDAR__.instances.push({version: this.version})
    }, xr.prototype.refreshPrecollectedContext = function () {
        var t, e = this;
        $e(null !== (t = this.shared.collect) && void 0 !== t ? t : {}, (function (t) {
            var n;
            null !== (n = e.shared.collect) && void 0 !== n && n[t].forEach((function (t) {
                return t.params = {}
            }))
        }))
    }, xr);

    function xr() {
        var t = this;
        this.version = "3.6.33", this.instance = function () {
            for (var e, o, s, c, l = [], u = 0; u < arguments.length; u++) l[u] = arguments[u];
            switch (l[0]) {
                case"init":
                    return void t.init(l[1]);
                case"config":
                    return void t.config(l[1]);
                case"sendCustomCountLog":
                case"sendCustomTimeLog":
                case"emit":
                case"send":
                    return void (t.shared.monitors.EmitMonitor ? (d = t.shared.monitors.EmitMonitor).handOut.apply(d, a([], i(l))) : t.instance("precollect", "emit", l));
                case"precollect":
                    var p = t.shared.collect, d = t.finalConfig.commonParams.refreshPrecollectedContext ? {} : {
                        pid: t.finalConfig.commonParams.pid,
                        url: t.finalConfig.commonParams.url,
                        context: t.finalConfig.commonParams.context
                    };
                    if ("sentry" === l[1]) return void p.sentry.push({event: l[2], params: d});
                    if ("exception" === l[1]) {
                        var h = (f = l[3] || {}).context, f = r(f, ["context"]);
                        h = n(n({}, We(d.context) && d.context), h);
                        return d.context = h, void p.exception.push({exception: l[2], params: n(n({}, d), f)})
                    }
                    if ("error" !== l[1]) return void ("emit" === l[1] && p.emit.push({event: l[2], params: d}));
                    if (!l[2] || !l[2][0]) return;
                    return "error" === (h = l[2][0] || {}).type && ((h.error || h.message) && p.jsError.push({
                        event: h,
                        params: d
                    }), null !== (f = h.target) && void 0 !== f && f.tagName && p.staticError.push({
                        event: h,
                        params: d
                    })), void ("unhandledrejection" === h.type && p.jsError.push({event: h, params: d}));
                case"Sentry":
                    var m, v = l[1];
                    return void (Qe(v) && ((m = null === (m = null === (m = t.shared.monitors) || void 0 === m ? void 0 : m.JSErrorMonitor) || void 0 === m ? void 0 : m.getSentry()) ? v(m) : t.instance("precollect", "sentry", l[1])));
                case"report":
                    return void (t.client && t.client.uploadQueue());
                case"performanceSend":
                    return void (null !== (v = null === (m = null === (v = t.shared.monitors) || void 0 === v ? void 0 : v.PerformanceMonitor) || void 0 === m ? void 0 : m.send) && void 0 !== v && v.call(m));
                case"performanceInit":
                    return void (null !== (s = null === (o = null === (c = t.shared.monitors) || void 0 === c ? void 0 : c.PerformanceMonitor) || void 0 === o ? void 0 : o.initAsync) && void 0 !== s && s.call(o, t.finalConfig.commonParams.pid));
                case"captureException":
                    return void (t.shared.monitors.JSExceptionMonitor ? (c = t.shared.monitors.JSExceptionMonitor.buildEvent(l[1]), s = c, c = null !== (o = l[2]) && void 0 !== o ? o : {}, o = We(s) ? We(c) ? n(n({}, s), {overrides: c}) : s : {}, null !== (s = null === (c = t.client) || void 0 === c ? void 0 : c.sendEvent) && void 0 !== s && s.call(c, o)) : t.instance("precollect", "exception", l[1], l[2]));
                case"context":
                    return void (t.client && null !== (e = l[1]) && void 0 !== e && e.call(l, t.client.contextAgent));
                default:
                    return
            }
        }, this.changeReortType = function (e) {
            t.finalConfig.commonParams.report_type = e, null !== (e = t.client) && void 0 !== e && e.updateConfig(t.finalConfig)
        }, this.onClose = function () {
            var e, n;
            e = t.onCloseReport, n = t.visibilityChange, Qe(e) && (Qe(window.addEventListener) && (window.addEventListener("unload", e), window.addEventListener("beforeunload", e), window.addEventListener("pagehide", e)), Qe(document.addEventListener) && document.addEventListener("visibilitychange", (function (t) {
                Qe(n) ? n(t) : "hidden" === document.visibilityState && e(t)
            })))
        }, this.visibilityChange = function () {
            "hidden" === document.visibilityState && t.onCloseReport(), "visible" === document.visibilityState && t.changeReortType("xhr")
        }, this.onCloseReport = function () {
            var e;
            t.changeReortType("beacon"), null !== (e = t.client) && void 0 !== e && e.uploadQueue()
        }, this.defaultClientConfig = vr({commonParams: {version: this.version}}), this.finalConfig = vr({commonParams: {version: this.version}}), this.client = void 0, this.userConfig = {}, this.inited = !1, this.configed = !1, this.shared = {
            config: this.finalConfig,
            instance: [],
            collect: {sentry: [], jsError: [], staticError: [], emit: [], exception: []},
            monitors: {}
        }, this.baseParams = {
            bid: this.shared.config.commonParams.bid || "",
            pid: this.shared.config.commonParams.pid || ""
        }, this.serverSetting = this.__SLARDAR__REPALCE__HOLDER__, this.saveInstance()
    }

    ir = cn, nn() && ((ir = (Rr = new Pr).instance).version = Rr.version, ir.shared = Rr.shared, ir.SlardarBrowser = Pr, ir._baseParams = Rr.baseParams);
    var Rr = ir;
    null !== (ir = null === (ir = null === (ir = window.Slardar) || void 0 === ir ? void 0 : ir.shared) || void 0 === ir ? void 0 : ir.instance) && void 0 !== ir && ir.length ? Rr = window.Slardar : (null !== (ir = window.Slardar) && void 0 !== ir && ir.q && (Rr.q = window.Slardar.q), null !== (ir = window.Slardar) && void 0 !== ir && ir.globalPreCollectError && (Rr.globalPreCollectError = window.Slardar.globalPreCollectError), null !== (ir = window.Slardar) && void 0 !== ir && ir.i && (Rr.i = window.Slardar.i), null !== (ir = window.Slardar) && void 0 !== ir && ir.lt && (Rr.lt = window.Slardar.lt), window.Slardar = Rr);
    var Cr, jr = Rr;

    function Or() {
        var t = new Pr, e = t.instance;
        return e.version = t.version, e.shared = t.shared, e
    }

    return null !== (Rr = window.Slardar) && void 0 !== Rr && Rr.q && (Xe(Cr = window.Slardar.q) && Cr.forEach((function (t) {
        jr.apply(void 0, a([], i(t)))
    })), delete window.Slardar.q), null !== (Cr = window.Slardar) && void 0 !== Cr && Cr.i && (Xe(Cr = window.Slardar.i) && Cr.forEach((function (t) {
        var e, n = Or();
        t.q && (Xe(e = t.q) && e.forEach((function (t) {
            n.apply(void 0, a([], i(t)))
        })), delete t.q), t.i = n
    })), delete window.Slardar.i), window.Slardar = jr, window.Slardar.SlardarBrowser = Pr, window.Slardar.createInstance = Or, jr
}();

window.Slardar("config",{bid:"webcast_douyin_pc"});

window.byted_acrawler.init({aid:6383,dfp:!0,boe:/boe/.test(window.location.host),intercept:!0,enablePathList:["/webcast/*"]}); // ,"/aweme/v1/*","/aweme/v2/*"
