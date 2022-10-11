import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.ul`
	display: flex;

	width: 100%;

	flex-direction: column;
	align-items: center;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_018};
		margin-top: ${theme.size.SIZE_024};

		@media (min-width: ${theme.breakpoints.DESKTOP_SMALL}) {
			display: grid;
			grid-template-columns: 1fr 1fr;
			place-items: center;
			margin: ${theme.size.SIZE_040} auto 0 auto;
			gap: ${theme.size.SIZE_022};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			display: grid;
			grid-template-columns: 1fr 1fr 1fr;
			place-items: center;
			margin: ${theme.size.SIZE_040} auto 0 auto;
			gap: ${theme.size.SIZE_022};
		}
	`}
`;
