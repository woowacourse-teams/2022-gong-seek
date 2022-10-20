import { css } from '@emotion/react';

export const TextOverflow = css`
	display: inline-block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: normal;
	text-align: left;
	word-wrap: break-word;
	display: -webkit-box;
	-webkit-box-orient: vertical;
`;

export const TwoLineTextOverFlow = css`
	${TextOverflow}
	-webkit-line-clamp: 2;
	line-height: 1.5;
	height: 3rem;
`;

export const OneLineTextOverFlow = css`
	${TextOverflow}
	-webkit-line-clamp: 1;
	line-height: 1.5;
	height: 1.5rem;
`;
