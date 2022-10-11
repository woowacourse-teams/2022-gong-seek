import { RecoilState } from 'recoil';

export type GetInsideRecoilState<T> = T extends RecoilState<infer R> ? R : never;
