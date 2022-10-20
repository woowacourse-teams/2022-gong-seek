import { RecoilState } from 'recoil';

export type RecoilStateInnerType<T> = T extends RecoilState<infer R> ? R : never;
