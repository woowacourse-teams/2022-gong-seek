import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const AnonymousCheckInput = styled.input`
	${({ theme }) => css`
		width: ${theme.size.SIZE_018};
		height: ${theme.size.SIZE_018};
	`}
`;

export const AnonymousBox = styled.div`
	display: flex;

	justify-content: center;
	align-items: center;
	padding-right: 5%;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_010};
		font-size: ${theme.size.SIZE_018};
	`}
`;

export const AnonymousText = styled.div`
	white-space: nowrap;
`;
