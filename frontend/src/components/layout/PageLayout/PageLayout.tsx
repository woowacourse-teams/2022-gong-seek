import { Size } from '@emotion/react';
import styled from '@emotion/styled';

const PageLayout = styled.section<Size>`
	display: ${({ display }) => display || 'flex'};

	flex-direction: ${({ flexDirection }) => flexDirection};
	justify-content: ${({ justifyContent }) => justifyContent || 'center'};
	align-items: ${({ alignItems }) => alignItems || 'center'};
	gap: ${({ gap }) => gap};

	width: ${({ width }) => width || 'fit-content'};
	max-width: ${({ maxWidth }) => maxWidth};
	height: ${({ height }) => height || 'fit-content'};

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.secondary};

	padding: ${({ padding }) => padding || 0};
`;

export default PageLayout;
